package com.point.fpi.domain.user.service.param;

import com.point.fpi.domain.user.entity.User;
import com.point.fpi.domain.user.entity.UserPoint;
import lombok.Builder;

@Builder
public class UserPointAddParam {
    private User user;
    private Long pointLimit;

    public UserPoint to() {
        return new UserPoint(
                user,
                pointLimit
        );
    }
}
