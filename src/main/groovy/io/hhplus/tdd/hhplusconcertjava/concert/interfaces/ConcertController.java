package io.hhplus.tdd.hhplusconcertjava.concert.interfaces;

import io.hhplus.tdd.hhplusconcertjava.common.error.BusinessError;
import io.hhplus.tdd.hhplusconcertjava.concert.application.ConcertFacade;
import io.hhplus.tdd.hhplusconcertjava.concert.domain.entity.Concert;
import io.hhplus.tdd.hhplusconcertjava.concert.interfaces.dto.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
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

    @Override
    @GetMapping("/concert/{concertId}/detail")
    public GetConcertInfoDetailDto getConcertInfoDetail(@PathVariable("concertId") Long concertId) {

        return this.concertFacade.getConcertDetailInfo(concertId);
    }
}
