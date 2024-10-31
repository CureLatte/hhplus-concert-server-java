package io.hhplus.tdd.hhplusconcertjava.concert.infrastructure.repository;

import io.hhplus.tdd.hhplusconcertjava.concert.domain.entity.Reservation;
import io.hhplus.tdd.hhplusconcertjava.concert.domain.repository.ReservationRepository;
import io.hhplus.tdd.hhplusconcertjava.concert.infrastructure.entity.Reservation2Entity;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

// @Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class Reservation2JpaRepository implements ReservationRepository {

    IReservation2JpaRepository jpaRepository;

    @Override
    public Reservation save(Reservation reservation) {
        Reservation2Entity reservation2Entity = this.jpaRepository.save(Reservation2Entity.fromDomain(reservation));
        return reservation2Entity.toDomain();
    }

    @Override
    public Reservation duplicateCheck(Reservation reservation) {
        Reservation2Entity reservation2Entity = this.jpaRepository.duplicateCheck(reservation.concertSeat.getId(), reservation.concertTime.getId());
        if(reservation2Entity == null) {
            return null;
        }

        return reservation2Entity.toDomain();
    }

    @Override
    public Reservation findById(Long reservationId) {
        return null;
    }
}
