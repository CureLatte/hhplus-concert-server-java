package io.hhplus.tdd.hhplusconcertjava.concert.application;

import io.hhplus.tdd.hhplusconcertjava.concert.domain.entity.Concert;
import io.hhplus.tdd.hhplusconcertjava.concert.domain.entity.ConcertTime;
import io.hhplus.tdd.hhplusconcertjava.concert.domain.service.ConcertService;
import io.hhplus.tdd.hhplusconcertjava.concert.domain.service.IConcertService;
import io.hhplus.tdd.hhplusconcertjava.concert.interfaces.dto.GetConcertTimeResponseDto;
import io.hhplus.tdd.hhplusconcertjava.user.domain.service.IUserService;
import io.hhplus.tdd.hhplusconcertjava.wait.domain.service.IWaitService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
}
