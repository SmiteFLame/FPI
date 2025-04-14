package com.point.fpi.contoller.user;

import com.point.fpi.application.user.UserApplication;
import com.point.fpi.application.user.request.UserRequest;
import com.point.fpi.application.user.request.UserPointRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserApplication userApplication;

    @PostMapping
    public void addUser(
            @RequestBody @Valid UserRequest request
    ) {
        userApplication.addUser(request);
    }

    @PostMapping("/point")
    public void addUserPoint(
            @RequestBody @Valid UserPointRequest request
    ) {
        userApplication.addUserPoint(request);
    }

    @PutMapping("/point")
    public void updateUserPoint(
            @RequestBody @Valid UserPointRequest request
    ) {
        userApplication.modifyUserPoint(request);
    }
}
