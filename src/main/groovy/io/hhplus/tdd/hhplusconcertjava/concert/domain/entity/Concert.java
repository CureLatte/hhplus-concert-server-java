package io.hhplus.tdd.hhplusconcertjava.concert.domain.entity;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
public class Concert {
    public Long id;
    public String title;
    public ConcertStatus status;
    public LocalDateTime createdAt;
    public LocalDateTime updatedAt;


    public enum ConcertStatus{
        READY, OPEN, CLOSED
    }

}
