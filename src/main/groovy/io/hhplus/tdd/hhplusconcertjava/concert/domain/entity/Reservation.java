package io.hhplus.tdd.hhplusconcertjava.concert.domain.entity;

import io.hhplus.tdd.hhplusconcertjava.user.domain.entity.User;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
public class Reservation {
    public Long id;
    public Concert concert;
    public ConcertTime concertTime;
    public ConcertSeat concertSeat;
    public User user;
    public int pointHistoryId;
    public ReservationStatus status;
    public LocalDateTime expiredAt;
    public LocalDateTime createdAt;
    public LocalDateTime updatedAt;

    public enum ReservationStatus{
        RESERVATION, PAY_DONE
    }


}
