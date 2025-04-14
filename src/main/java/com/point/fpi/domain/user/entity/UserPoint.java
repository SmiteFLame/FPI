package com.point.fpi.domain.user.entity;

import com.point.fpi.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "user_point")
@NoArgsConstructor
public class UserPoint extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_point_id", nullable = false)
    private Long userPointId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "point_limit")
    private Long pointLimit;

    public UserPoint(
            User user,
            Long pointLimit
    ) {
        this.user = user;
        this.pointLimit = pointLimit;
    }

    public void modifyPointLimit(
            Long pointLimit
    ) {
        this.pointLimit = pointLimit;
    }
}
