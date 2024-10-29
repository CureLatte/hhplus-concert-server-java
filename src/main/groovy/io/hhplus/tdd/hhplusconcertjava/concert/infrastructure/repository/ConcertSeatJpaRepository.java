package io.hhplus.tdd.hhplusconcertjava.concert.infrastructure.repository;

import io.hhplus.tdd.hhplusconcertjava.concert.domain.entity.ConcertSeat;
import io.hhplus.tdd.hhplusconcertjava.concert.domain.entity.ConcertTime;
import io.hhplus.tdd.hhplusconcertjava.concert.domain.repository.ConcertSeatRepository;
import io.hhplus.tdd.hhplusconcertjava.concert.infrastructure.entity.ConcertSeatEntity;
import io.hhplus.tdd.hhplusconcertjava.concert.infrastructure.entity.ConcertTimeEntity;
import jakarta.persistence.LockModeType;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ConcertSeatJpaRepository implements ConcertSeatRepository {
    final IConcertSeatJpaRepository jpaRepository;

    @Override
    @Lock(LockModeType.PESSIMISTIC_READ)
    public ConcertSeat findById(Long id) {

        ConcertSeatEntity concertSeatEntity = jpaRepository.findById(id).orElse(null);
        if(concertSeatEntity == null){
            return null;
        }

        return concertSeatEntity.toDomain();
    }

    @Override
    public ConcertSeat findByIdForUpdate(Long id) {
        ConcertSeatEntity concertSeatEntity = jpaRepository.findByIdForUpdate(id);

        if(concertSeatEntity == null){
            return null;
        }

        return concertSeatEntity.toDomain();
    }

    @Override
    public List<ConcertSeat> findAllByAvailableSeat(ConcertTime concertTime) {
        List<ConcertSeatEntity> concertSeatEntityList = this.jpaRepository.findAllByAvailableSeat(concertTime.getId());

        return concertSeatEntityList.stream().map(ConcertSeatEntity::toDomain).toList();
    }

    @Override
    public ConcertSeat save(ConcertSeat concertSeat) {
        ConcertSeatEntity updatedConcertSeatEntity = this.jpaRepository.save(ConcertSeatEntity.fromDomain(concertSeat));

        return updatedConcertSeatEntity.toDomain();
    }
}
