package com.point.fpi.domain.user.repository;

import com.point.fpi.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Boolean existsByLoginId(String loginId);
    Optional<User> findByLoginId(String loginId);
}
