package com.point.fpi.application.point.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class PointCancelRequest {
    @NotNull
    private String pointKey;
}
