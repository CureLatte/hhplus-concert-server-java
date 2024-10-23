package io.hhplus.tdd.hhplusconcertjava.concert.domain.repository;

import io.hhplus.tdd.hhplusconcertjava.concert.domain.entity.Concert;

public interface ConcertRepository {
    public Concert findById(Long id);

    public Concert create(Concert concert);

    public Concert save(Concert concert);
}
