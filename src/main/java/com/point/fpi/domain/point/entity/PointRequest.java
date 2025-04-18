package com.point.fpi.domain.point.entity;

import com.point.fpi.common.entity.BaseEntity;
import com.point.fpi.common.enums.PointRequestState;
import com.point.fpi.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@Table(name = "point_request")
@NoArgsConstructor
public class PointRequest extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "point_request_id", nullable = false)
    private Long pointRequestId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "order_id", nullable = false, unique = true)
    private String orderId;

    @Column(name = "request_amount", nullable = false)
    private Long requestAmount;

    @Column(name = "cancel_amount", nullable = false)
    private Long cancelAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "point_request_state", nullable = false)
    private PointRequestState pointRequestState;

    public PointRequest(
            User user,
            String orderId,
            Long requestAmount,
            PointRequestState pointRequestState
    ) {
        this.user = user;
        this.orderId = orderId;
        this.requestAmount = requestAmount;
        this.cancelAmount = 0L;
        this.pointRequestState = pointRequestState;
    }

    public void modifyCancel(
            Long point
    ) {
        this.cancelAmount += point;
        if (Objects.equals(requestAmount, cancelAmount)) {
            pointRequestState = PointRequestState.CANCEL;
        } else {
            pointRequestState = PointRequestState.PART_CANCEL;
        }
    }
}
