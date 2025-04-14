package com.point.fpi.domain.point.entity;

import com.point.fpi.common.entity.BaseEntity;
import com.point.fpi.common.enums.PointEvent;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "point_history")
@NoArgsConstructor
public class PointHistory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pointHistoryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "point_id", nullable = false)
    private Point point;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "point_request_id", nullable = true)
    private PointRequest pointRequest;

    @Enumerated(EnumType.STRING)
    @Column(name = "point_event", nullable = false)
    private PointEvent pointEvent;

    @Column(name = "change_amount", nullable = false)
    private Long changeAmount;

    public PointHistory(
            Point point,
            PointRequest pointRequest,
            PointEvent pointEvent,
            Long changeAmount
    ) {
        this.point = point;
        this.pointRequest = pointRequest;
        this.pointEvent = pointEvent;
        this.changeAmount = changeAmount;
    }
}
