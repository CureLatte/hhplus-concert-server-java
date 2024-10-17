package io.hhplus.tdd.hhplusconcertjava.concert.domain.service;

import io.hhplus.tdd.hhplusconcertjava.common.BusinessError;
import io.hhplus.tdd.hhplusconcertjava.concert.domain.entity.Concert;
import io.hhplus.tdd.hhplusconcertjava.concert.domain.entity.ConcertSeat;
import io.hhplus.tdd.hhplusconcertjava.concert.domain.entity.ConcertTime;
import io.hhplus.tdd.hhplusconcertjava.concert.domain.repository.ConcertRepository;
import io.hhplus.tdd.hhplusconcertjava.concert.domain.repository.ConcertSeatRepository;
import io.hhplus.tdd.hhplusconcertjava.concert.domain.repository.ConcertTimeRepository;
import io.hhplus.tdd.hhplusconcertjava.concert.domain.repository.ReservationRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor(onConstructor=@__(@Autowired))
public class ConcertService implements IConcertService {

    ConcertRepository concertRepository;
    ConcertTimeRepository concertTimeRepository;
    ConcertSeatRepository concertSeatRepository;
    ReservationRepository reservationRepository;

    final public String NOT_FOUND_CONCERT_ERROR_MESSAGE = "존재하지 않은 콘서트 입니다.";
    final public String NOT_FOUND_CONCERT_TIME_ERROR_MESSAGE = "존재하지 않은 콘서트 날짜 입니다.";

    @Override
    public Concert getConcert(Long concertId) {
        Concert concert = this.concertRepository.findById(concertId);

        if(concert == null) {
            throw new BusinessError(400, this.NOT_FOUND_CONCERT_ERROR_MESSAGE);
        }

        return concert;
    }

    @Override
    public ConcertTime getConcertTime(Long concertTimeId) {

        ConcertTime concertTime = this.concertTimeRepository.findById(concertTimeId);

        if(concertTime == null) {
            throw new BusinessError(400, this.NOT_FOUND_CONCERT_TIME_ERROR_MESSAGE);
        }


        return concertTime;
    }

    @Override
    public List<ConcertTime> getConcertTimes(Concert concert) {
        return this.concertTimeRepository.findAllAvailableTime(concert);
    }

    @Override
    public List<ConcertSeat> getConcertSeats(ConcertTime concertTime) {
        return this.concertSeatRepository.findAllByAvailableSeat(concertTime);
    }


}
