package com.point.fpi.common.exception;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BizErrorResponse{
    private String message;
}
