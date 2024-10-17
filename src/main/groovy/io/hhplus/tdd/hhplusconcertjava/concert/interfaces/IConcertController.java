package io.hhplus.tdd.hhplusconcertjava.concert.interfaces;

import io.hhplus.tdd.hhplusconcertjava.concert.interfaces.dto.GetConcertSeatListResponseDto;
import io.hhplus.tdd.hhplusconcertjava.concert.interfaces.dto.GetConcertTimeResponseDto;
import io.hhplus.tdd.hhplusconcertjava.concert.interfaces.dto.PostReserveSeatRequestDto;
import io.hhplus.tdd.hhplusconcertjava.concert.interfaces.dto.PostReserveSeatResponseDto;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface IConcertController {
    public GetConcertTimeResponseDto getConcertTimeList(@PathVariable Long concertId);

    public GetConcertSeatListResponseDto getConcertSeatList(@PathVariable Long concertTimeId);

    public PostReserveSeatResponseDto postReservation(@RequestBody PostReserveSeatRequestDto requestDto);
}
