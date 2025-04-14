package com.point.fpi.domain.point.param;

import com.point.fpi.common.enums.PointRequestState;
import com.point.fpi.domain.point.entity.PointRequest;
import com.point.fpi.domain.user.entity.User;
import lombok.Builder;

@Builder
public class PointRequestAddParam {
    private User user;
    private String orderId;
    private Long point;
    private PointRequestState pointRequestState;

    public PointRequest to() {
        return new PointRequest(
                user,
                orderId,
                point,
                pointRequestState
        );
    }
}
