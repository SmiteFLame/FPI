package com.point.fpi.domain.point.entity;

import com.point.fpi.common.entity.BaseEntity;
import com.point.fpi.domain.user.entity.User;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "point")
public class Point extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "point_id")
    private Long pointId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "point_type")
    private String pointType;

    @Column(name = "point_state")
    private String pointState;

    @Column(name = "init_point")
    private Long initPoint;

    @Column(name = "remain_point")
    private Long remainPoint;

    @Column(name = "due_date")
    private LocalDateTime dueDate;
}
