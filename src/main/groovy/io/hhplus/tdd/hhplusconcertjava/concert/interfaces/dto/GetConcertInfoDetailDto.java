package io.hhplus.tdd.hhplusconcertjava.concert.interfaces.dto;

import io.hhplus.tdd.hhplusconcertjava.concert.domain.entity.Concert;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
public record GetConcertInfoDetailDto (
        Long concertId,
        String concertTitle,
        String concertStatus,
        String concertViewRank,
        String concertPlace,
        List<String> concertCast,
        Integer concertLeftSeat

){


}
