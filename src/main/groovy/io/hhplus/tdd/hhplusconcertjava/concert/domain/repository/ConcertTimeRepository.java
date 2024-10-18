package io.hhplus.tdd.hhplusconcertjava.concert.domain.repository;

import io.hhplus.tdd.hhplusconcertjava.concert.domain.entity.Concert;
import io.hhplus.tdd.hhplusconcertjava.concert.domain.entity.ConcertTime;

import java.util.List;

public interface ConcertTimeRepository {
    public ConcertTime findById(Long concertTimeId);

    public List<ConcertTime> findAllAvailableTime(Concert concert);

    public ConcertTime save(ConcertTime concertTime);
}
