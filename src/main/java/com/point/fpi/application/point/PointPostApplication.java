
package com.point.fpi.application.point;

import com.point.fpi.application.point.request.PointAddRequest;
import com.point.fpi.common.enums.PointEvent;
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
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.point.fpi.common.constant.Constants.MAX_AMOUNT_PER_ONCE;

@Service
@RequiredArgsConstructor
public class PointPostApplication {
    private final PointService pointService;
    private final PointHistoryService pointHistoryService;
    private final PointRequestService pointRequestService;

    @Async
    public void expirePoint(
            List<Point> pointList
    ) {
        List<PointHistoryAddParam> pointHistoryList = pointList.stream()
                .map(point ->
                        PointHistoryAddParam.builder()
                                .point(point)
                                .pointEvent(PointEvent.OVER_DUEDATE)
                                .changeAmount(-point.getRemainPoint())
                                .build()
                ).toList();

        pointHistoryService.addAllPointHistory(pointHistoryList);
        pointService.expiredAllPoint(pointList);
    }
}
