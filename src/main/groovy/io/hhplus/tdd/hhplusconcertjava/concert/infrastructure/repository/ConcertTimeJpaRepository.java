package io.hhplus.tdd.hhplusconcertjava.concert.infrastructure.repository;

import io.hhplus.tdd.hhplusconcertjava.concert.domain.entity.Concert;
import io.hhplus.tdd.hhplusconcertjava.concert.domain.entity.ConcertTime;
import io.hhplus.tdd.hhplusconcertjava.concert.domain.repository.ConcertTimeRepository;
import io.hhplus.tdd.hhplusconcertjava.concert.infrastructure.entity.ConcertTimeEntity;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ConcertTimeJpaRepository implements ConcertTimeRepository {
    final IConcertTimeJpaRepository jpaRepository;

    @Override
    public ConcertTime findById(Long concertTimeId) {
        ConcertTimeEntity concertTimeEntity = this.jpaRepository.findById(concertTimeId).orElse(null);

        if(concertTimeEntity == null) {
            return null;
        }

        return concertTimeEntity.toDomain();
    }

    @Override
    public List<ConcertTime> findAllAvailableTime(Concert concert) {

        List<ConcertTimeEntity> concertTimeEntityList = this.jpaRepository.findAlLByAvailableTime(concert.getId());

        return concertTimeEntityList.stream().map(ConcertTimeEntity::toDomain).toList();
    }

    @Override
    public ConcertTime save(ConcertTime concertTime) {
        ConcertTimeEntity concertTimeEntity = this.jpaRepository.save(ConcertTimeEntity.fromDomain(concertTime));

        return concertTimeEntity.toDomain();
    }
}
