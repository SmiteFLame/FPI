package com.point.fpi.domain.user.service;

import com.point.fpi.common.exception.BizException;
import com.point.fpi.domain.user.entity.User;
import com.point.fpi.domain.user.repository.UserRepository;
import com.point.fpi.domain.user.param.UserAddParam;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User getUserByLoginId(
            String loginId
    ) {
        return userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new BizException("존재하지 않는 아이디입니다."));
    }

    public boolean checkLoginId(
            String loginId
    ) {
        return userRepository.existsByLoginId(loginId);
    }

    public void addUser(
            UserAddParam param
    ) {
        userRepository.save(param.to());
    }
}
