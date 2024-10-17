package io.hhplus.tdd.hhplusconcertjava.concert.domain.entity;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder
@ToString
public class ConcertTime {
    public Long id;
    public Concert concert;
    public LocalDateTime startTime;
    public LocalDateTime endTime;
    public ConcertTimeStatus status;
    public int maxCnt;
    public int leftCnt;
    public int price;


    public enum ConcertTimeStatus{
        ON_SALE, SOLD_OUT
    }

}
