package com.point.fpi.application.user.request;

import com.point.fpi.domain.user.param.UserAddParam;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UserRequest {
    @NotBlank
    private String loginId;
    @NotBlank
    private String userName;

    public UserAddParam toAddParam() {
        return UserAddParam.builder()
                .loginId(this.loginId)
                .userName(this.userName)
                .build();
    }
}
