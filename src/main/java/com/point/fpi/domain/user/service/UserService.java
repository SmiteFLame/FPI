package com.point.fpi.domain.user.service;

import com.point.fpi.domain.user.entity.User;
import com.point.fpi.domain.user.repository.UserRepository;
import com.point.fpi.domain.user.service.param.UserAddParam;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public Optional<User> findUserByLoginId(
            String loginId
    ) {
        return userRepository.findByLoginId(loginId);
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
