package com.point.fpi.domain.point.repository;

import com.point.fpi.domain.point.entity.PointRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PointRequestRepository extends JpaRepository<PointRequest, Long> {
    Optional<PointRequest> findByOrderId(String orderId);
}
