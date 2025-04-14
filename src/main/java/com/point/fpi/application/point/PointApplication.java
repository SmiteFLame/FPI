package com.point.fpi.application.point;

import com.point.fpi.application.point.request.PointAddRequest;
import com.point.fpi.application.point.request.PointCancelRequest;
import com.point.fpi.application.point.request.PointUserRequest;
import com.point.fpi.common.enums.PointEvent;
import com.point.fpi.common.enums.PointState;
import com.point.fpi.common.exception.BizException;
import com.point.fpi.domain.configuration.entity.Configuration;
import com.point.fpi.domain.configuration.service.ConfigurationService;
import com.point.fpi.domain.point.entity.Point;
import com.point.fpi.domain.point.entity.PointHistory;
import com.point.fpi.domain.point.entity.PointRequest;
import com.point.fpi.domain.point.param.PointHistoryAddParam;
import com.point.fpi.domain.point.param.PointModifyParam;
import com.point.fpi.domain.point.service.PointHistoryService;
import com.point.fpi.domain.point.service.PointRequestService;
import com.point.fpi.domain.point.service.PointService;
import com.point.fpi.domain.user.entity.User;
import com.point.fpi.domain.user.entity.UserPoint;
import com.point.fpi.domain.user.service.UserPointService;
import com.point.fpi.domain.user.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PointApplication {
    private final PointService pointService;
    private final PointHistoryService pointHistoryService;
    private final PointRequestService pointRequestService;
    private final PointPostApplication pointPostApplication;
    private final UserService userService;
    private final UserPointService userPointService;

    @Transactional
    public void addPoint(
            PointAddRequest request
    ) {
        LocalDateTime now = LocalDateTime.now();
        User user = userService.getUserByLoginId(request.getLoginId());

        /* User 별 최대 금액 제한 */
        Optional<UserPoint> userPointOptional =
                userPointService.findUserPointByUser(user);

        if (userPointOptional.isPresent()) {
            UserPoint userPoint = userPointOptional.get();
            List<Point> pointList = pointService.listByPointByUserAndPointStateNotIn(
                    user,
                    List.of(PointState.CANCELLED, PointState.EXPIRED)
            );

            pointPostApplication.expirePoint(
                    pointList.stream()
                            .filter(point -> point.isExpired(now))
                            .toList()
            );

            Long remainUserPoint = pointList.stream()
                    .filter(point -> !point.isExpired(now))
                    .mapToLong(Point::getRemainPoint)
                    .sum();

            if (remainUserPoint + request.getPoint() > userPoint.getPointLimit()) {
                throw new BizException("해당 유저의 최대 금액 제한이 도달했습니다.");
            }
        }

        Point point = pointService.addPoint(request.toAddParam(user, now));
        pointHistoryService.addPointHistory(request.toAddHistoryParam(point));
    }

    @Transactional
    public void cancelPoint(
            PointCancelRequest request
    ) {
        Point point = pointService.getPointByPointKey(request.getPointKey());
        if (!point.getPointState().equals(PointState.AVAILABLE)) {
            throw new BizException(String.format("%s 상태의 포인트는 삭제할 수 없습니다.", point.getPointKey()));
        }
        if (!point.getInitPoint().equals(point.getRemainPoint())) {
            throw new BizException("이미 적립된 금액중 일부가 사용되어 삭제할 수 없습니다.");
        }
        pointService.cancelPoint(point);
    }

    @Transactional
    public void usePoint(
            PointUserRequest request
    ) {
        LocalDateTime now = LocalDateTime.now();
        User user = userService.getUserByLoginId(request.getLoginId());

        List<Point> pointList = pointService.listByPointByUserAndPointStateNotIn(
                        user,
                        List.of(PointState.CANCELLED, PointState.EXPIRED)
                ).stream()
                .filter(p -> !p.isExpired(now))
                .sorted(Comparator.comparing(Point::getDueDate))
                .toList();

        List<Point> usablePoints = pointList
                .stream()
                .filter(p -> !p.isExpired(now))
                .sorted(Comparator.comparing(Point::getDueDate))
                .toList();

        pointPostApplication.expirePoint(pointList.stream().filter(point -> point.isExpired(now)).toList());

        if (pointList.stream().mapToLong(Point::getRemainPoint).sum() < request.getPoint()) {
            throw new BizException("사용 가능한 포인트가 부족합니다.");
        }
        PointRequest pointRequest = pointRequestService.addPointRequest(request.toPointRequestAddParam(user));

        List<PointModifyParam> usagePoints = new ArrayList<>();
        List<PointHistoryAddParam> historyParams = new ArrayList<>();

        long remainingToUse = request.getPoint();
        for (Point point : usablePoints) {
            long available = point.getRemainPoint();
            long toUse = Math.min(available, remainingToUse);


            usagePoints.add(PointModifyParam.builder()
                    .point(point)
                    .pointAmount(toUse)
                    .build());

            remainingToUse -= toUse;
            historyParams.add(PointHistoryAddParam.builder()
                    .point(point)
                    .pointRequest(pointRequest)
                    .pointEvent(PointEvent.USE_POINT)
                    .changeAmount(toUse)
                    .build()
            );

            if (remainingToUse <= 0) break;
        }

        pointService.modifyPointList(usagePoints);
        pointHistoryService.addAllPointHistory(historyParams);
    }
}
