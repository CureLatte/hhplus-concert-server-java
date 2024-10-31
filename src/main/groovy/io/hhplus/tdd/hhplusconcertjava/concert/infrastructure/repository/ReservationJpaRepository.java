package io.hhplus.tdd.hhplusconcertjava.concert.infrastructure.repository;

import io.hhplus.tdd.hhplusconcertjava.concert.domain.entity.Reservation;
import io.hhplus.tdd.hhplusconcertjava.concert.domain.repository.ReservationRepository;
import io.hhplus.tdd.hhplusconcertjava.concert.infrastructure.entity.ReservationEntity;
import jakarta.persistence.LockModeType;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ReservationJpaRepository implements ReservationRepository {

    IReservationJpaRepository jpaRepository;


    @Override
    public Reservation save(Reservation reservation) {
        ReservationEntity reservationEntity = this.jpaRepository.save(ReservationEntity.fromDomain(reservation));

        return reservationEntity.toDomain();
    }

    @Override
    public Reservation duplicateCheck(Reservation reservation) {

        List<ReservationEntity> reservationEntityList = this.jpaRepository.findByDuplication( reservation.concertTime.id, reservation.concertSeat.id);

        if(reservationEntityList == null || reservationEntityList.size() == 0){
            return null;
        }

        return reservationEntityList.get(0).toDomain();
    }

    @Override
    public Reservation findById(Long reservationId) {
        ReservationEntity reservationEntity = this.jpaRepository.findById(reservationId).orElse(null);

        if(reservationEntity == null){
            return null;
        }

        return reservationEntity.toDomain();
    }
}

