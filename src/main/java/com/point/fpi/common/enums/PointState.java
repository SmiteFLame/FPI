package com.point.fpi.common.enums;

import lombok.Getter;

@Getter
public enum PointState {
    AVAILABLE("사용 가능"),
    USED("전액 사용"),
    EXPIRED("만료됨"),
    CANCELLED("적립 취소");

    private final String description;

    PointState(String description) {
        this.description = description;
    }

}
