package io.hhplus.tdd.hhplusconcertjava.concert.domain.repository;

import io.hhplus.tdd.hhplusconcertjava.concert.domain.entity.Reservation;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository {

    public Reservation save(Reservation reservation);

    public Reservation duplicateCheck(Reservation reservation);

    public Reservation findById(Long reservationId);
}
