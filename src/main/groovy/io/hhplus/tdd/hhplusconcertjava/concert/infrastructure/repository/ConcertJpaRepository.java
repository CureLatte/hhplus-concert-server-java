package io.hhplus.tdd.hhplusconcertjava.concert.infrastructure.repository;

import io.hhplus.tdd.hhplusconcertjava.concert.domain.repository.ConcertRepository;
import org.springframework.stereotype.Component;

@Component
public class ConcertJpaRepository implements ConcertRepository {
    IConcertJpaRepository jpaRepository;


}
