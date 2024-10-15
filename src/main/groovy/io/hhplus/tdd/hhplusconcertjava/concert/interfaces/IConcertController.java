package io.hhplus.tdd.hhplusconcertjava.concert.interfaces;

import io.hhplus.tdd.hhplusconcertjava.concert.interfaces.dto.GetConcertSeatListResponseDto;
import io.hhplus.tdd.hhplusconcertjava.concert.interfaces.dto.GetConcertTimeResponseDto;
import org.springframework.web.bind.annotation.PathVariable;

public interface IConcertController {
    public GetConcertTimeResponseDto getConcertTimeList(@PathVariable String concertId);

    public GetConcertSeatListResponseDto getConcertSeatList(@PathVariable String concertTimeId);
}
