package com.point.fpi.common.enums;

import lombok.Getter;

@Getter
public enum PointEvent {
    INIT_REQUEST("최초 생성"),
    OVER_DUEDATE("만료");

    private final String description;

    PointEvent(String description) {
        this.description = description;
    }

}
