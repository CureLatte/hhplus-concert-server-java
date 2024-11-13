package io.hhplus.tdd.hhplusconcertjava.concert.infrastructure.entity;

import io.hhplus.tdd.hhplusconcertjava.common.entity.BaseEntity;
import io.hhplus.tdd.hhplusconcertjava.concert.domain.entity.Reservation;
import io.hhplus.tdd.hhplusconcertjava.user.infrastructure.entity.UserEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name="reservation")
@EntityListeners(AuditingEntityListener.class)
public class ReservationEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="concert_seat_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private ConcertSeatEntity concertSeat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "concert_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private ConcertEntity concert;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "concert_time_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private ConcertTimeEntity concertTime;

    @Column(name="point_history_id")
    private int pointHistoryId;

    @Column(name="status")
    private String status;

    @Column(name="expired_at")
    private LocalDateTime expiredAt;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Version
    @ColumnDefault("0")
    private Integer version;

    public Reservation toDomain(){
        return Reservation.builder()
                .id(this.id)
                .status(Reservation.ReservationStatus.valueOf(this.status))
                .concert(this.concert.toDomain())
                .concertSeat(this.concertSeat.toDomain())
                .concertTime(this.concertTime.toDomain())
                .pointHistoryId(this.pointHistoryId)
                .user(this.user == null ? null: this.user.toDomain())
                .expiredAt(this.expiredAt)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                 .version(this.version)
                .build();
    }

    public static ReservationEntity fromDomain(Reservation reservation){
        ReservationEntity entity = new ReservationEntity();
        entity.id = reservation.getId();
        entity.concert = ConcertEntity.fromDomain(reservation.getConcert());
        entity.concertSeat = ConcertSeatEntity.fromDomain(reservation.getConcertSeat());
        entity.concertTime = ConcertTimeEntity.fromDomain(reservation.getConcertTime());
        entity.pointHistoryId = reservation.getPointHistoryId();
        entity.status = reservation.getStatus().name();
        entity.expiredAt = reservation.getExpiredAt();
         entity.version = reservation.getVersion();
        entity.createdAt = reservation.getCreatedAt();
        entity.updatedAt = reservation.getUpdatedAt();
        entity.user = UserEntity.fromDomain(reservation.getUser());

        return entity;
    }

}


