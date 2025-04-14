
package com.point.fpi.application.point;

import com.point.fpi.common.enums.PointEvent;
import com.point.fpi.domain.point.entity.Point;
import com.point.fpi.domain.point.param.PointHistoryAddParam;
import com.point.fpi.domain.point.service.PointHistoryService;
import com.point.fpi.domain.point.service.PointService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PointPostApplication {
    private final PointService pointService;
    private final PointHistoryService pointHistoryService;

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
