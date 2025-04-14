package com.point.fpi.domain.point.entity;

import com.point.fpi.common.entity.BaseEntity;
import com.point.fpi.common.enums.PointState;
import com.point.fpi.common.enums.PointType;
import com.point.fpi.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "point")
@NoArgsConstructor
public class Point extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "point_id")
    private Long pointId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "point_key", nullable = false, unique = true)
    private String pointKey;

    @Enumerated(EnumType.STRING)
    @Column(name = "point_type", nullable = false)
    private PointType pointType;

    @Enumerated(EnumType.STRING)
    @Column(name = "point_state", nullable = false)
    private PointState pointState;

    @Column(name = "init_point", nullable = false)
    private Long initPoint;

    @Column(name = "remain_point", nullable = false)
    private Long remainPoint;

    @Column(name = "due_date", nullable = false)
    private LocalDateTime dueDate;

    public Point(
            User user,
            String pointKey,
            PointType pointType,
            PointState pointState,
            Long point,
            LocalDateTime dueDate
    ) {
        this.user = user;
        this.pointKey = pointKey;
        this.pointType = pointType;
        this.pointState = pointState;
        this.initPoint = point;
        this.remainPoint = point;
        this.dueDate = dueDate;
    }

    public boolean isExpired(LocalDateTime now) {
        return dueDate.isBefore(now);
    }

    public void expirePoint() {
        this.remainPoint = 0L;
        this.pointState = PointState.EXPIRED;
    }

    public void cancelPoint() {
        remainPoint = 0L;
        pointState = PointState.CANCELLED;
    }

    public void usePoint(Long point) {
        remainPoint -= point;
        if (remainPoint == 0L) {
            pointState = PointState.USED;
        }
    }

    public void restorePoint(Long point) {
        remainPoint += point;
        pointState = PointState.AVAILABLE;
    }
}
