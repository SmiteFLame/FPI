package com.point.fpi.application.point.request;

import com.point.fpi.common.enums.PointRequestState;
import com.point.fpi.domain.point.param.PointRequestAddParam;
import com.point.fpi.domain.user.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class PointUseCancelRequest {
    @NotBlank
    private String loginId;

    @NotBlank
    private String orderId;

    @NotNull
    private Long point;
}
