package io.hhplus.tdd.hhplusconcertjava.concert.infrastructure.repository;

import io.hhplus.tdd.hhplusconcertjava.concert.infrastructure.entity.ConcertSeatEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IConcertSeatJpaRepository extends JpaRepository<ConcertSeatEntity, Long> {
}
