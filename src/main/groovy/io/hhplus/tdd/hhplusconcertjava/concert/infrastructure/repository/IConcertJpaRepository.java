package io.hhplus.tdd.hhplusconcertjava.concert.infrastructure.repository;

import io.hhplus.tdd.hhplusconcertjava.concert.infrastructure.entity.ConcertEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IConcertJpaRepository extends JpaRepository<ConcertEntity, Long> {
}
