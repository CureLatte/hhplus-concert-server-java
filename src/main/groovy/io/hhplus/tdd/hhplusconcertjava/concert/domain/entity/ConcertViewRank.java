package io.hhplus.tdd.hhplusconcertjava.concert.domain.entity;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
public class ConcertViewRank {
    public Long id;
    public String name;
    public Integer limitAge;

    public LocalDateTime createdAt;
    public LocalDateTime updatedAt;
    public LocalDateTime deletedAt;
}
