package com.point.fpi.application.point;

import com.point.fpi.application.point.request.PointAddRequest;
import com.point.fpi.application.point.request.PointCancelRequest;
import com.point.fpi.common.enums.PointState;
import com.point.fpi.common.exception.BizException;
import com.point.fpi.domain.configuration.entity.Configuration;
import com.point.fpi.domain.configuration.service.ConfigurationService;
import com.point.fpi.domain.point.entity.Point;
import com.point.fpi.domain.point.entity.PointHistory;
import com.point.fpi.domain.point.param.PointHistoryAddParam;
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

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
        if(!point.getPointState().equals(PointState.AVAILABLE)) {
            throw new BizException(String.format("%s 상태의 포인트는 삭제할 수 없습니다.", point.getPointKey()));
        }
        if(!point.getInitPoint().equals(point.getRemainPoint())) {
            throw new BizException("이미 적립된 금액중 일부가 사용되어 삭제할 수 없습니다.");
        }
        pointService.cancelPoint(point);
    }
}
