package io.hhplus.tdd.hhplusconcertjava.concert.infrastructure.repository;

import io.hhplus.tdd.hhplusconcertjava.concert.domain.entity.Concert;
import io.hhplus.tdd.hhplusconcertjava.concert.domain.repository.ConcertRepository;
import io.hhplus.tdd.hhplusconcertjava.concert.infrastructure.entity.ConcertEntity;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
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

    @Override
    public Concert create(Concert concert) {
        ConcertEntity concertEntity = this.jpaRepository.save(ConcertEntity.fromDomain(concert));

        return concertEntity.toDomain();
    }

    @Override
    public Concert save(Concert concert) {
        ConcertEntity concertEntity = this.jpaRepository.save(ConcertEntity.fromDomain(concert));

        return concertEntity.toDomain();
    }

    @Override
    public void deleteAll() {
        this.jpaRepository.clearTable();
    }
}
