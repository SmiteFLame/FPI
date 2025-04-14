package com.point.fpi.domain.user.param;

import com.point.fpi.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserAddParam {
    private String loginId;
    private String userName;

    public User to() {
        return new User(
                this.loginId,
                this.userName
        );
    }
}
