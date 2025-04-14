package com.point.fpi.domain.point.param;

import com.point.fpi.common.enums.PointState;
import com.point.fpi.common.enums.PointType;
import com.point.fpi.domain.point.entity.Point;
import com.point.fpi.domain.user.entity.User;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public class PointAddParam {
    private User user;
    private String pointKey;
    private PointType pointType;
    private PointState pointState;
    private Long initPoint;
    private LocalDateTime dueDate;

    public Point to() {
        return new Point(
                user,
                pointKey,
                pointType,
                pointState,
                initPoint,
                dueDate
        );
    }
}
