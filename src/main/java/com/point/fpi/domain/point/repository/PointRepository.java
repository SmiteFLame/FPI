package com.point.fpi.domain.point.repository;

import com.point.fpi.common.enums.PointState;
import com.point.fpi.domain.point.entity.Point;
import com.point.fpi.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PointRepository extends JpaRepository<Point, Long> {
    List<Point> findAllByUserAndPointStateNotIn(User user, List<PointState> pointStateList);
    Optional<Point> findPointByPointKey(String pointKey);
}
