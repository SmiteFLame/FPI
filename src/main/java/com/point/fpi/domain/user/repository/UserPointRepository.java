package com.point.fpi.domain.user.repository;

import com.point.fpi.domain.user.entity.User;
import com.point.fpi.domain.user.entity.UserPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserPointRepository extends JpaRepository<UserPoint, Long> {
    Boolean existsByUser(User user);
    Optional<UserPoint> findUserPointByUser(User user);
}
