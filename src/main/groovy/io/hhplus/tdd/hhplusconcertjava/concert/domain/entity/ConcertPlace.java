package io.hhplus.tdd.hhplusconcertjava.concert.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
public class ConcertPlace {
    public Long id;
    public String name;
    public String address;
    public Double latitude;
    public Double longitude;

    public LocalDateTime createdAt;
    public LocalDateTime updatedAt;
    public LocalDateTime deletedAt;
}
