package io.hhplus.tdd.hhplusconcertjava.concert.domain.repository;

import io.hhplus.tdd.hhplusconcertjava.concert.domain.entity.ConcertSeat;
import io.hhplus.tdd.hhplusconcertjava.concert.domain.entity.ConcertTime;

import java.util.List;

public interface ConcertSeatRepository {

    public List<ConcertSeat> findAllByAvailableSeat(ConcertTime concertTime);
}
