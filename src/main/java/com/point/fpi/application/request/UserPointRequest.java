package com.point.fpi.application.request;

import com.point.fpi.domain.user.entity.User;
import com.point.fpi.domain.user.service.param.UserPointAddParam;
import lombok.Getter;

@Getter
public class UserPointRequest {
    private String loginId;
    private Long pointLimit;

    public UserPointAddParam to(User user) {
        return UserPointAddParam.builder()
                .pointLimit(pointLimit)
                .build();
    }
}
