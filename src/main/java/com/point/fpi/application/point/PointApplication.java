package com.point.fpi.application.point;

import com.point.fpi.application.point.request.PointAddRequest;
import com.point.fpi.application.point.request.PointCancelRequest;
import com.point.fpi.application.point.request.PointUseCancelRequest;
import com.point.fpi.application.point.request.PointUseRequest;
import com.point.fpi.common.enums.PointEvent;
import com.point.fpi.common.enums.PointState;
import com.point.fpi.common.enums.PointType;
import com.point.fpi.common.exception.BizException;
import com.point.fpi.domain.point.entity.Point;
import com.point.fpi.domain.point.entity.PointHistory;
import com.point.fpi.domain.point.entity.PointRequest;
import com.point.fpi.domain.point.param.PointAddParam;
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

import java.time.LocalDateTime;
import java.util.*;
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
            throw new BizException(String.format("%s 상태의 포인트는 삭제할 수 없습니다.", point.getPointState().name()));
        }
        if (!point.getInitPoint().equals(point.getRemainPoint())) {
            throw new BizException("이미 적립된 금액중 일부가 사용되어 삭제할 수 없습니다.");
        }
        pointService.cancelPoint(point);
        pointHistoryService.addPointHistory(PointHistoryAddParam.builder()
                .point(point)
                .pointEvent(PointEvent.CANCEL_POINT_REQUEST)
                .changeAmount(point.getInitPoint())
                .build());
    }

    @Transactional
    public void usePoint(
            PointUseRequest request
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

    @Transactional
    public void cancelUsePoint(
            PointUseCancelRequest request
    ) {
        PointRequest pointRequest = pointRequestService.getPointRequestByOrderId(request.getOrderId());

        if (pointRequest.getRequestAmount() - pointRequest.getCancelAmount() < request.getPoint()) {
            throw new BizException("취소 가능한 포인트 이상의 금액이 입력 되었습니다.");
        }

        List<PointHistory> allHistories = pointHistoryService.getAllByPointRequest(pointRequest);

        Map<Long, Long> cancelMap = allHistories.stream()
                .filter(h -> h.getPointEvent() == PointEvent.CANCEL_POINT)
                .collect(Collectors.groupingBy(
                        h -> h.getPoint().getPointId(),
                        Collectors.summingLong(PointHistory::getChangeAmount)
                ));

        List<PointHistory> usedHistories = allHistories.stream()
                .filter(h -> h.getPointEvent() == PointEvent.USE_POINT)
                .sorted(Comparator.comparing(point -> point.getPoint().getDueDate()))
                .toList();

        long remainToCancel = request.getPoint();
        LocalDateTime now = LocalDateTime.now();

        List<PointHistoryAddParam> cancelParams = new ArrayList<>();

        for (PointHistory used : usedHistories) {
            if (remainToCancel <= 0) break;

            long alreadyCancelled = cancelMap.getOrDefault(used.getPoint().getPointId(), 0L);
            long availableToCancel = used.getChangeAmount() - alreadyCancelled;

            if (availableToCancel <= 0) continue;

            long cancelNow = Math.min(availableToCancel, remainToCancel);
            Point point = used.getPoint();

            if (point.isExpired(now)) {
                Point newPoint = pointService.addPoint(PointAddParam.builder()
                        .user(point.getUser())
                        .pointKey(String.format("%s_%s", point.getPointKey(), PointType.RENEWAL.name()))
                        .pointType(PointType.RENEWAL)
                        .pointState(PointState.AVAILABLE)
                        .initPoint(cancelNow)
                        .dueDate(point.getDueDate().plusDays(365L)) // FIXME 임의 설정
                        .build());
                cancelParams.add(PointHistoryAddParam.builder()
                        .point(newPoint)
                        .pointEvent(PointEvent.INIT_REQUEST)
                        .changeAmount(cancelNow)
                        .build());
            } else {
                point.restorePoint(cancelNow);
                cancelParams.add(PointHistoryAddParam.builder()
                        .point(point)
                        .pointRequest(pointRequest)
                        .pointEvent(PointEvent.CANCEL_POINT)
                        .changeAmount(-cancelNow)
                        .build());
            }

            remainToCancel -= cancelNow;
        }

        if (cancelParams.isEmpty()) {
            throw new BizException("사용 취소할 수 있는 포인트가 없습니다.");
        }

        pointRequest.modifyCancel(request.getPoint());
        pointHistoryService.addAllPointHistory(cancelParams);
    }
}
