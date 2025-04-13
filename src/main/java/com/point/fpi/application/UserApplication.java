package com.point.fpi.application;

import com.point.fpi.application.request.UserRequest;
import com.point.fpi.application.request.UserPointRequest;
import com.point.fpi.common.exception.BizException;
import com.point.fpi.domain.user.entity.User;
import com.point.fpi.domain.user.entity.UserPoint;
import com.point.fpi.domain.user.service.UserPointService;
import com.point.fpi.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserApplication {
    private final UserService userService;
    private final UserPointService userPointService;

    public void addUser(
            UserRequest request
    ) {
        if (userService.checkLoginId(request.getLoginId())) {
            throw new BizException("이미 존재하는 아이디입니다.");
        }
        userService.addUser(request.to());
    }

    public void addUserPoint(
            UserPointRequest request
    ) {
        User user = userService.findUserByLoginId(request.getLoginId())
                .orElseThrow(() -> new BizException("존재하지 않는 아이디입니다."));

        if (userPointService.checkUserPointByUser(user)) {
            throw new BizException("이미 포인트 매핑이 있는 유저입니다.");
        }
        userPointService.addUserPoint(request.to(user));
    }

    public void modifyUserPoint(
            UserPointRequest request
    ) {
        User user = userService.findUserByLoginId(request.getLoginId())
                .orElseThrow(() -> new BizException("존재하지 않는 아이디입니다."));
        UserPoint userPoint = userPointService.findUserPointByUser(user)
                .orElseThrow(() -> new BizException("포인트 매핑이 존재하지 않는 아이디 입니다."));

        userPoint.modifyPointLimit(request.getPointLimit());
    }
}
