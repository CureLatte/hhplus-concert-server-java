package io.hhplus.tdd.hhplusconcertjava.concert.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@Setter
@ToString
public class ConcertCastList {
    public Long id;
    Concert concert;
    List<ConcertCast>  castList;

    public LocalDateTime createdAt;
    public LocalDateTime updatedAt;
    public LocalDateTime deletedAt;
}
