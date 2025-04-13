package com.point.fpi.domain.user.service;

import com.point.fpi.domain.user.entity.User;
import com.point.fpi.domain.user.entity.UserPoint;
import com.point.fpi.domain.user.repository.UserPointRepository;
import com.point.fpi.domain.user.service.param.UserPointAddParam;
import com.point.fpi.domain.user.service.param.UserPointModifyParam;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserPointService {
    private final UserPointRepository userPointRepository;

    public Optional<UserPoint> findUserPointByUser(
            User user
    ) {
        return userPointRepository.findUserPointByUser(user);
    }

    public Boolean checkUserPointByUser(
            User user
    ) {
        return userPointRepository.existsByUser(user);
    }

    public void addUserPoint(
            UserPointAddParam param
    ) {
        userPointRepository.save(param.to());
    }

    public void modifyUserPoint(
            UserPoint userPoint,
            UserPointModifyParam param
    ) {
        userPoint.modifyPointLimit(param.getPointLimit());
        userPointRepository.save(userPoint);
    }
}
