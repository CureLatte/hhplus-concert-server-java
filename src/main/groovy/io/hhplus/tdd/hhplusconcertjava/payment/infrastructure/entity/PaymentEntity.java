package io.hhplus.tdd.hhplusconcertjava.payment.infrastructure.entity;


import io.hhplus.tdd.hhplusconcertjava.concert.infrastructure.entity.ReservationEntity;
import io.hhplus.tdd.hhplusconcertjava.payment.domain.entity.Payment;
import io.hhplus.tdd.hhplusconcertjava.point.infrastructure.entity.PointHistoryEntity;
import io.hhplus.tdd.hhplusconcertjava.user.infrastructure.entity.UserEntity;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name="payment")
@EntityListeners(AuditingEntityListener.class)
public class PaymentEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="reservation_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private ReservationEntity reservation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="point_history_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private PointHistoryEntity pointHistory;

    @Column(name="status")
    private String status;

    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;

    public Payment toDomain(){
        return Payment.builder()
                .id(this.id)
                .user(this.user.toDomain())
                .reservation(this.reservation.toDomain())
                .pointHistory(this.pointHistory.toDomain())
                .status(Payment.PaymentStatus.valueOf(this.status))
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .build();
    }

    public static PaymentEntity fromDomain(Payment payment){
        PaymentEntity entity = new PaymentEntity();

        entity.id = payment.getId();
        entity.user = UserEntity.fromDomain(payment.getUser());
        entity.reservation = ReservationEntity.fromDomain(payment.getReservation());
        entity.pointHistory = PointHistoryEntity.fromDomain(payment.getPointHistory());
        entity.status = payment.getStatus().name();
        entity.createdAt = payment.getCreatedAt();
        entity.updatedAt = payment.getUpdatedAt();

        return entity;

    }
}
