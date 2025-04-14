package com.point.fpi.common.warmup;

import com.point.fpi.domain.user.entity.User;
import com.point.fpi.domain.user.entity.UserPoint;
import com.point.fpi.domain.user.param.UserAddParam;
import com.point.fpi.domain.user.param.UserPointAddParam;
import com.point.fpi.domain.user.service.UserPointService;
import com.point.fpi.domain.user.service.UserService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WarmUpRunner {
    private final UserService userService;
    private final UserPointService userPointService;

    /**
     * README: 빠른 테스트를 위한 초기 데이터 설정
     */
    @PostConstruct
    public void warmup() {
        User userA = userService.addUser(
                UserAddParam.builder()
                        .loginId("loginIdA")
                        .userName("UserNameA")
                        .build());

        User userB = userService.addUser(
                UserAddParam.builder()
                        .loginId("loginIdB")
                        .userName("UserNameB")
                        .build());

        UserPoint userPoint = userPointService.addUserPoint(
                UserPointAddParam.builder()
                        .user(userA)
                        .pointLimit(1000L)
                        .build()
        );
    }
}
