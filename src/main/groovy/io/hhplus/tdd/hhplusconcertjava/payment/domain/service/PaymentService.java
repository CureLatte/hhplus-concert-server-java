package io.hhplus.tdd.hhplusconcertjava.payment.domain.service;

import io.hhplus.tdd.hhplusconcertjava.concert.domain.entity.Reservation;
import io.hhplus.tdd.hhplusconcertjava.payment.domain.entity.Payment;
import io.hhplus.tdd.hhplusconcertjava.point.domain.entity.PointHistory;
import io.hhplus.tdd.hhplusconcertjava.user.domain.entity.User;

public interface PaymentService {
    public Payment payReservation(User user, Reservation reservation, PointHistory pointHistory);
}
