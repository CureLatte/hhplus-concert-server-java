package io.hhplus.tdd.hhplusconcertjava.concert.interfaces;

import io.hhplus.tdd.hhplusconcertjava.concert.interfaces.dto.GetConcertSeatListResponseDto;
import io.hhplus.tdd.hhplusconcertjava.concert.interfaces.dto.GetConcertTimeResponseDto;
import io.hhplus.tdd.hhplusconcertjava.concert.interfaces.dto.PostReserveSeatRequestDto;
import io.hhplus.tdd.hhplusconcertjava.concert.interfaces.dto.PostReserveSeatResponseDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class ConcertController implements IConcertController {
    @Override
    @GetMapping("/concert/time/{concertId}")
    public GetConcertTimeResponseDto getConcertTimeList(@PathVariable("concertId") String concertId) {
        System.out.println(concertId);

        return new GetConcertTimeResponseDto(List.of(new GetConcertTimeResponseDto.ConcertTimeDto(1, "1", 1, 1)));
    }

    @Override
    @GetMapping("/concert/seat/{concertTimeId}")
    public GetConcertSeatListResponseDto getConcertSeatList(@PathVariable("concertTimeId") String concertTimeId) {
        return new GetConcertSeatListResponseDto(List.of(
            new GetConcertSeatListResponseDto.ConcertSeat(
                    1, "0001", true
            )
        ));
    }

    @Override
    @PostMapping("/concert/reservation")
    public PostReserveSeatResponseDto postReservation(@RequestBody PostReserveSeatRequestDto requestDto) {

        return new PostReserveSeatResponseDto(new PostReserveSeatResponseDto.ReservationDto(1, "wait", 1, 1));
    }
}
