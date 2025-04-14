package com.point.fpi.domain.point.param;

import com.point.fpi.common.enums.PointEvent;
import com.point.fpi.common.enums.PointState;
import com.point.fpi.common.enums.PointType;
import com.point.fpi.domain.point.entity.Point;
import com.point.fpi.domain.point.entity.PointHistory;
import com.point.fpi.domain.point.entity.PointRequest;
import com.point.fpi.domain.user.entity.User;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public class PointHistoryAddParam {
    private Point point;
    private PointRequest pointRequest;
    private PointEvent pointEvent;
    private Long changeAmount;

    public PointHistory to() {
        return new PointHistory(
                point,
                pointRequest,
                pointEvent,
                changeAmount
        );
    }
}
