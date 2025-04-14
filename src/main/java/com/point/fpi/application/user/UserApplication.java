package com.point.fpi.application.user;

import com.point.fpi.application.user.request.UserRequest;
import com.point.fpi.application.user.request.UserPointRequest;
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
        userService.addUser(request.toAddParam());
    }

    public void addUserPoint(
            UserPointRequest request
    ) {
        User user = userService.getUserByLoginId(request.getLoginId());

        if (userPointService.checkUserPointByUser(user)) {
            throw new BizException("이미 포인트 매핑이 있는 유저입니다.");
        }
        userPointService.addUserPoint(request.toAddParam(user));
    }

    public void modifyUserPoint(
            UserPointRequest request
    ) {
        User user = userService.getUserByLoginId(request.getLoginId());
        UserPoint userPoint = userPointService.getUserPointByUser(user);

        userPointService.modifyUserPoint(userPoint, request.toModifyParam());
    }
}
