package com.point.fpi.domain.point.entity;

import com.point.fpi.common.entity.BaseEntity;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "point_history")
public class PointHistory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pointHistoryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "point_id")
    private Point point;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "point_request_id")
    private PointRequest pointRequest;

    @Column(length = 50)
    private String pointEvent; // EARN, USE, CANCEL, EXPIRE

    private Long changeAmount;
}
