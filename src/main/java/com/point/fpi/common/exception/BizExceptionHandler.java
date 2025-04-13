package com.point.fpi.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BizExceptionHandler {

    @ExceptionHandler(BizException.class)
    public ResponseEntity<BizErrorResponse> handleBaseException(BizException e) {
        BizErrorResponse response = new BizErrorResponse(
                e.getErrorMessage()
        );
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BizErrorResponse> handleException(Exception e) {
        BizErrorResponse response = new BizErrorResponse(
                e.getMessage()
        );
        e.printStackTrace();
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);
    }
}
