package com.point.fpi.common.enums;

import lombok.Getter;

@Getter
public enum PointRequestState {
    USE("포인트 사용"),
    CANCEL("포인트 사용 취소"),
    PART_CANCEL("포인트 사용 일부 취소");

    private final String description;

    PointRequestState(String description) {
        this.description = description;
    }

}
