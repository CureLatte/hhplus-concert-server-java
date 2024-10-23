package io.hhplus.tdd.hhplusconcertjava.concert.application;

import io.hhplus.tdd.hhplusconcertjava.concert.domain.entity.Concert;
import io.hhplus.tdd.hhplusconcertjava.concert.domain.entity.ConcertSeat;
import io.hhplus.tdd.hhplusconcertjava.concert.domain.entity.ConcertTime;
import io.hhplus.tdd.hhplusconcertjava.concert.domain.entity.Reservation;
import io.hhplus.tdd.hhplusconcertjava.concert.domain.service.ConcertService;
import io.hhplus.tdd.hhplusconcertjava.concert.domain.service.IConcertService;
import io.hhplus.tdd.hhplusconcertjava.concert.interfaces.dto.GetConcertSeatListResponseDto;
import io.hhplus.tdd.hhplusconcertjava.concert.interfaces.dto.GetConcertTimeResponseDto;
import io.hhplus.tdd.hhplusconcertjava.concert.interfaces.dto.PostReserveSeatRequestDto;
import io.hhplus.tdd.hhplusconcertjava.concert.interfaces.dto.PostReserveSeatResponseDto;
import io.hhplus.tdd.hhplusconcertjava.user.domain.entity.User;
import io.hhplus.tdd.hhplusconcertjava.user.domain.service.IUserService;
import io.hhplus.tdd.hhplusconcertjava.wait.domain.service.IWaitService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ConcertFacade {


    IConcertService concertService;
    IUserService userService;
    IWaitService waitService;

    public GetConcertTimeResponseDto getConcertTimeList(Long concertId){

        Concert concert = this.concertService.getConcert(concertId);

        List<ConcertTime> concertTimeList = this.concertService.getConcertTimes(concert);

        List<GetConcertTimeResponseDto.ConcertTimeDto> responseData = concertTimeList.stream().map((concertTime)-> {
            return new GetConcertTimeResponseDto.ConcertTimeDto(concertTime.getId(), concertTime.startTime.toString(), concertTime.maxCnt, concertTime.leftCnt);
        }).toList();

        return new GetConcertTimeResponseDto(responseData);
    }

    public GetConcertSeatListResponseDto getConcertSeatList(Long concertTimeId){

        ConcertTime concertTime = this.concertService.getConcertTime(concertTimeId);

        List<ConcertSeat> concertSeatList = this.concertService.getConcertSeats(concertTime);

        List<GetConcertSeatListResponseDto.ConcertSeat> responseData = concertSeatList.stream().map((concertSeat -> {
            return new GetConcertSeatListResponseDto.ConcertSeat(concertSeat.getId(), concertSeat.number);
        })).toList();

        return   new GetConcertSeatListResponseDto(responseData);
    }

    @Transactional
    public PostReserveSeatResponseDto postReserveSeat(Long concertId, Long concertTimeId, Long concertSeatId, String uuid, Long userId){

        User user = this.userService.getUser(userId);

        Concert concert = this.concertService.getConcert(concertId);
        ConcertTime concertTime = this.concertService.getConcertTime(concertTimeId);
        ConcertSeat concertSeat = this.concertService.getConcertSeat(concertSeatId);

        Reservation reservation = this.concertService.reserve(concert, concertTime, concertSeat,user, uuid);

        return new PostReserveSeatResponseDto(new PostReserveSeatResponseDto.ReservationDto(reservation.id, reservation.status.name(), reservation.concertSeat.number, reservation.concert.getId()));
    }

}
