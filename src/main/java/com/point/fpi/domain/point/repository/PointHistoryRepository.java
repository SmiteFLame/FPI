package com.point.fpi.domain.point.repository;

import com.point.fpi.domain.point.entity.PointHistory;
import com.point.fpi.domain.point.entity.PointRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PointHistoryRepository extends JpaRepository<PointHistory, Long> {
    List<PointHistory> findAllByPointRequest(PointRequest pointRequest);
}
