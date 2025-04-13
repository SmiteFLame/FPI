package com.point.fpi.common.exception;

import lombok.Getter;

@Getter
public class BizException extends RuntimeException {

    private final String errorMessage;

    public BizException(
            String errorMessage
    ) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }
}
