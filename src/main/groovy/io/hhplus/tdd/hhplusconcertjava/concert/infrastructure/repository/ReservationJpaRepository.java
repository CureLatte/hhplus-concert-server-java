package io.hhplus.tdd.hhplusconcertjava.concert.infrastructure.repository;

import io.hhplus.tdd.hhplusconcertjava.concert.domain.entity.Reservation;
import io.hhplus.tdd.hhplusconcertjava.concert.domain.repository.ReservationRepository;
import io.hhplus.tdd.hhplusconcertjava.concert.infrastructure.entity.ReservationEntity;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

        ReservationEntity reservationEntity = this.jpaRepository.findByDuplication(reservation.user.id, reservation.concertTime.id, reservation.concertSeat.id);

        if(reservationEntity == null){
            return null;
        }

        return reservationEntity.toDomain();
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

