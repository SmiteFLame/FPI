package com.point.fpi.domain.point.entity;

import com.point.fpi.common.entity.BaseEntity;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "point_request")
public class PointRequest extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "point_request_id", nullable = false)
    private Long pointRequestId;

    @Column(name = "order_id", nullable = false, unique = true)
    private String orderId;

    @Column(name = "request_amount", nullable = false)
    private Long requestAmount;

    @Column(name = "point_request_state", nullable = false)
    private String pointRequestState;
}
