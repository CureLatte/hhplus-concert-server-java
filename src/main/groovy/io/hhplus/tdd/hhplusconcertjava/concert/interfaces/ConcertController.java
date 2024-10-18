package io.hhplus.tdd.hhplusconcertjava.concert.interfaces;

import io.hhplus.tdd.hhplusconcertjava.common.BusinessError;
import io.hhplus.tdd.hhplusconcertjava.common.annotaion.UserCheck;
import io.hhplus.tdd.hhplusconcertjava.common.annotaion.WaitQueueCheck;
import io.hhplus.tdd.hhplusconcertjava.concert.application.ConcertFacade;
import io.hhplus.tdd.hhplusconcertjava.concert.interfaces.dto.GetConcertSeatListResponseDto;
import io.hhplus.tdd.hhplusconcertjava.concert.interfaces.dto.GetConcertTimeResponseDto;
import io.hhplus.tdd.hhplusconcertjava.concert.interfaces.dto.PostReserveSeatRequestDto;
import io.hhplus.tdd.hhplusconcertjava.concert.interfaces.dto.PostReserveSeatResponseDto;
import io.hhplus.tdd.hhplusconcertjava.user.domain.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ConcertController implements IConcertController {

    ConcertFacade concertFacade;
    private final HttpServletRequest httpServletRequest;

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

        return this.concertFacade.getConcertSeatList(concertTimeId);
    }

    @Override
    @PostMapping("/concert/reservation")
    @WaitQueueCheck
    @UserCheck
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
