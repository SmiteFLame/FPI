package com.point.fpi.application.point.request;

import com.point.fpi.common.enums.PointEvent;
import com.point.fpi.common.enums.PointState;
import com.point.fpi.common.enums.PointType;
import com.point.fpi.domain.point.entity.Point;
import com.point.fpi.domain.point.param.PointAddParam;
import com.point.fpi.domain.point.param.PointHistoryAddParam;
import com.point.fpi.domain.user.entity.User;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PointAddRequest {
    @NotBlank
    private String loginId;

    @NotBlank
    private String pointKey;

    @NotNull
    private Long point;

    @NotNull
    private String pointType;

    @NotNull
    @Max(1824) // 5년
    private Long expiredInDays = 365L;

    public PointAddParam toAddParam(
            User user,
            LocalDateTime now
    ) {
        return PointAddParam.builder()
                .user(user)
                .pointKey(pointKey)
                .pointState(PointState.AVAILABLE)
                .pointType(PointType.valueOf(pointType))
                .initPoint(point)
                .dueDate(now.plusDays(expiredInDays))
                .build();
    }

    public PointHistoryAddParam toAddHistoryParam(
            Point point
    ) {
        return PointHistoryAddParam.builder()
                .point(point)
                .pointEvent(PointEvent.INIT_REQUEST)
                .changeAmount(point.getRemainPoint())
                .build();
    }
}
