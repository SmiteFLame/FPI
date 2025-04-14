package com.point.fpi.common.enums;

import lombok.Getter;

@Getter
public enum PointType {
    AUTO("자동 수집"),
    ADMIN("관리자 수기 지급"),
    RENEWAL("만료 후 신규 적립");
    
    private final String description;

    PointType(String description) {
        this.description = description;
    }

}
