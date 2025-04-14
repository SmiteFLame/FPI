package com.point.fpi.application.user.request;

import com.point.fpi.domain.user.entity.User;
import com.point.fpi.domain.user.param.UserPointAddParam;
import com.point.fpi.domain.user.param.UserPointModifyParam;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UserPointRequest {
    @NotBlank
    private String loginId;
    @NotNull
    private Long pointLimit;

    public UserPointAddParam toAddParam(User user) {
        return UserPointAddParam.builder()
                .user(user)
                .pointLimit(pointLimit)
                .build();
    }

    public UserPointModifyParam toModifyParam() {
        return UserPointModifyParam.builder()
                .pointLimit(pointLimit)
                .build();
    }
}
