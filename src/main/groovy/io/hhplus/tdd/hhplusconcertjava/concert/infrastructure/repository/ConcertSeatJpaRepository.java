package io.hhplus.tdd.hhplusconcertjava.concert.infrastructure.repository;

import io.hhplus.tdd.hhplusconcertjava.concert.domain.repository.ConcertSeatRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ConcertSeatJpaRepository implements ConcertSeatRepository {
    final IConcertSeatJpaRepository jpaRepository;

}
