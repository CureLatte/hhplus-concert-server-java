package io.hhplus.tdd.hhplusconcertjava.concert.interfaces.dto;

import java.util.List;



public record GetConcertTimeResponseDto(
        List<ConcertTimeDto> date
) {
    public record ConcertTimeDto(
            Long ConcertTimeId,
            String startTime,
            int maxCnt,
            int leftCnt
    ) {

    }
}
