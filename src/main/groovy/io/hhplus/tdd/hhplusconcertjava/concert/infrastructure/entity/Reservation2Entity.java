package io.hhplus.tdd.hhplusconcertjava.concert.infrastructure.entity;

import io.hhplus.tdd.hhplusconcertjava.concert.domain.entity.Concert;
import io.hhplus.tdd.hhplusconcertjava.concert.domain.entity.ConcertSeat;
import io.hhplus.tdd.hhplusconcertjava.concert.domain.entity.ConcertTime;
import io.hhplus.tdd.hhplusconcertjava.concert.domain.entity.Reservation;
import io.hhplus.tdd.hhplusconcertjava.user.domain.entity.User;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name="reservation2")
@EntityListeners(AuditingEntityListener.class)
public class Reservation2Entity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long user_id;
    private Long concert_id;
    private Long concert_time_id;
    private Long concert_seat_id;
    private String status;

    @CreatedDate
    private LocalDateTime created_at;

    @LastModifiedDate
    private LocalDateTime updated_at;

    public Reservation toDomain(){
        return Reservation.builder()
                .id(this.id)
                .user(User.builder().id(this.user_id).build())
                .concert(Concert.builder().id(concert_id).build())
                .concertTime(ConcertTime.builder().id(this.concert_time_id).build())
                .concertSeat(ConcertSeat.builder().id(this.concert_seat_id).build())
                .status(Reservation.ReservationStatus.valueOf(this.status))
                .createdAt(this.created_at)
                .updatedAt(this.updated_at)
                .build();
    }

    public static Reservation2Entity fromDomain(Reservation reservation){
        Reservation2Entity entity = new Reservation2Entity();
        entity.id = reservation.getId();
        entity.user_id = reservation.getUser().getId();
        entity.concert_id = reservation.getConcert().getId();
        entity.concert_seat_id = reservation.getConcertSeat().getId();
        entity.concert_time_id = reservation.getConcertTime().getId();
        entity.status = reservation.getStatus().name();
        entity.created_at = reservation.getCreatedAt();
        entity.updated_at = reservation.getUpdatedAt();


        return entity;


    }
}
