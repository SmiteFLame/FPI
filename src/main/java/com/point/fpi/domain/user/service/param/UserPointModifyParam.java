package com.point.fpi.domain.user.service.param;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserPointModifyParam {
    private Long pointLimit;
}
