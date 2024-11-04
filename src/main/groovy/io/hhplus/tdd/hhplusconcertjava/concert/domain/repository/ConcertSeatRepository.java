package io.hhplus.tdd.hhplusconcertjava.concert.domain.repository;

import io.hhplus.tdd.hhplusconcertjava.concert.domain.entity.ConcertSeat;
import io.hhplus.tdd.hhplusconcertjava.concert.domain.entity.ConcertTime;

import java.util.List;

public interface ConcertSeatRepository {

    public ConcertSeat findById(Long id);

    public ConcertSeat findByIdForUpdate(Long id);

    public ConcertSeat findByIdForShare(Long id);

    public List<ConcertSeat> findAllByAvailableSeat(ConcertTime concertTime);

    public ConcertSeat save(ConcertSeat concertSeat);

    public void deleteAll();
}
