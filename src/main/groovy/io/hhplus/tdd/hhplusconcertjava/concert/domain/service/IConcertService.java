package io.hhplus.tdd.hhplusconcertjava.concert.domain.service;

import io.hhplus.tdd.hhplusconcertjava.concert.domain.entity.Concert;
import io.hhplus.tdd.hhplusconcertjava.concert.domain.entity.ConcertSeat;
import io.hhplus.tdd.hhplusconcertjava.concert.domain.entity.ConcertTime;

import java.util.List;

public interface IConcertService {
    public Concert getConcert(Long concertId);

    public ConcertTime getConcertTime(Long concertTimeId);

    public List<ConcertTime> getConcertTimes(Concert concert);

    public List<ConcertSeat> getConcertSeats(ConcertTime concertTime);
}
