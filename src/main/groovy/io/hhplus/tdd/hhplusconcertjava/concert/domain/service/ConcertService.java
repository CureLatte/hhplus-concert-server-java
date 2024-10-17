package io.hhplus.tdd.hhplusconcertjava.concert.domain.service;

import io.hhplus.tdd.hhplusconcertjava.common.BusinessError;
import io.hhplus.tdd.hhplusconcertjava.concert.domain.entity.Concert;
import io.hhplus.tdd.hhplusconcertjava.concert.domain.repository.ConcertRepository;
import io.hhplus.tdd.hhplusconcertjava.concert.domain.repository.ConcertSeatRepository;
import io.hhplus.tdd.hhplusconcertjava.concert.domain.repository.ConcertTimeRepository;
import io.hhplus.tdd.hhplusconcertjava.concert.domain.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor=@__(@Autowired))
public class ConcertService implements IConcertService {

    ConcertRepository concertRepository;
    ConcertTimeRepository concertTimeRepository;
    ConcertSeatRepository concertSeatRepository;
    ReservationRepository reservationRepository;

    final public String NOT_FOUND_CONCERT_ERROR_MESSAGE = "존재하지 않은 콘서트 입니다.";

    @Override
    public Concert getConcert(Long id) {
        Concert concert = this.concertRepository.findById(id);

        if(concert == null) {
            throw new BusinessError(400, this.NOT_FOUND_CONCERT_ERROR_MESSAGE);
        }

        return concert;
    }



}
