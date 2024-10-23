package io.hhplus.tdd.hhplusconcertjava.concert.interfaces;

import io.hhplus.tdd.hhplusconcertjava.common.error.BusinessError;
import io.hhplus.tdd.hhplusconcertjava.concert.application.ConcertFacade;
import io.hhplus.tdd.hhplusconcertjava.concert.interfaces.dto.GetConcertSeatListResponseDto;
import io.hhplus.tdd.hhplusconcertjava.concert.interfaces.dto.GetConcertTimeResponseDto;
import io.hhplus.tdd.hhplusconcertjava.concert.interfaces.dto.PostReserveSeatRequestDto;
import io.hhplus.tdd.hhplusconcertjava.concert.interfaces.dto.PostReserveSeatResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ConcertController implements IConcertController {

    ConcertFacade concertFacade;
    private final HttpServletRequest httpServletRequest;

    @Override
    @GetMapping("/concert/time/{concertId}")
    public GetConcertTimeResponseDto getConcertTimeList(@PathVariable("concertId") Long concertId) {



        return this.concertFacade.getConcertTimeList(concertId);
    }

    @Override
    @GetMapping("/concert/seat/{concertTimeId}")
    public GetConcertSeatListResponseDto getConcertSeatList(@PathVariable("concertTimeId") Long concertTimeId) {

        return this.concertFacade.getConcertSeatList(concertTimeId);
    }

    @Override
    @PostMapping("/concert/reservation")
    public PostReserveSeatResponseDto postReservation(@RequestBody PostReserveSeatRequestDto requestDto) {

        String userIdString =  httpServletRequest.getHeader("Authorization");

        if(userIdString == null){
            throw new BusinessError(400, "NOT FOUND USER");
        }

        Long userId = Long.parseLong(userIdString);

        return this.concertFacade.postReserveSeat(
                requestDto.concertId(), requestDto.concertTimeId(), requestDto.concertSeatId(), requestDto.uuid, userId
        );
    }
}
