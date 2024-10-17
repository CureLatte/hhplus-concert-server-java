package io.hhplus.tdd.hhplusconcertjava.concert.domain.entity;

import lombok.*;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@ToString
public class ConcertSeat {
    public Long id;

    public ConcertTime concertTime;
    public String number;
    public ConcertSeatStatus status;
    public String uuid;
    public LocalDateTime createdAt;
    public LocalDateTime updatedAt;

    public enum ConcertSeatStatus{
        EMPTY,  RESERVATION, PAY
    }

}
