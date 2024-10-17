package io.hhplus.tdd.hhplusconcertjava.concert.interfaces;

import io.hhplus.tdd.hhplusconcertjava.common.annotaion.WaitQueueCheck;
import io.hhplus.tdd.hhplusconcertjava.concert.application.ConcertFacade;
import io.hhplus.tdd.hhplusconcertjava.concert.interfaces.dto.GetConcertSeatListResponseDto;
import io.hhplus.tdd.hhplusconcertjava.concert.interfaces.dto.GetConcertTimeResponseDto;
import io.hhplus.tdd.hhplusconcertjava.concert.interfaces.dto.PostReserveSeatRequestDto;
import io.hhplus.tdd.hhplusconcertjava.concert.interfaces.dto.PostReserveSeatResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ConcertController implements IConcertController {

    ConcertFacade concertFacade;

    @Override
    @GetMapping("/concert/time/{concertId}")
    @WaitQueueCheck
    public GetConcertTimeResponseDto getConcertTimeList(@PathVariable("concertId") Long concertId) {



        return this.concertFacade.getConcertTimeList(concertId);
    }

    @Override
    @GetMapping("/concert/seat/{concertTimeId}")
    @WaitQueueCheck
    public GetConcertSeatListResponseDto getConcertSeatList(@PathVariable("concertTimeId") Long concertTimeId) {
        return new GetConcertSeatListResponseDto(List.of(
            new GetConcertSeatListResponseDto.ConcertSeat(
                    1, "0001", true
            )
        ));
    }

    @Override
    @PostMapping("/concert/reservation")
    @WaitQueueCheck
    public PostReserveSeatResponseDto postReservation(@RequestBody PostReserveSeatRequestDto requestDto) {

        return new PostReserveSeatResponseDto(new PostReserveSeatResponseDto.ReservationDto(1, "wait", 1, 1));
    }
}
