package io.hhplus.tdd.hhplusconcertjava.concert.infrastructure.repository;

import io.hhplus.tdd.hhplusconcertjava.concert.domain.entity.Concert;
import io.hhplus.tdd.hhplusconcertjava.concert.domain.repository.ConcertRepository;
import io.hhplus.tdd.hhplusconcertjava.concert.infrastructure.entity.ConcertEntity;
import org.springframework.stereotype.Component;

@Component
public class ConcertJpaRepository implements ConcertRepository {
    IConcertJpaRepository jpaRepository;


    @Override
    public Concert findById(Long id) {
        ConcertEntity concertEntity = this.jpaRepository.findById(id).orElse(null);

        if(concertEntity == null){
            return null;
        }

        return concertEntity.toDomain();
    }
}
