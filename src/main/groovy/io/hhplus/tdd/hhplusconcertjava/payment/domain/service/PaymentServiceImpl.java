package io.hhplus.tdd.hhplusconcertjava.payment.domain.service;

import io.hhplus.tdd.hhplusconcertjava.concert.domain.entity.Reservation;
import io.hhplus.tdd.hhplusconcertjava.concert.domain.repository.ReservationRepository;
import io.hhplus.tdd.hhplusconcertjava.payment.domain.entity.Payment;
import io.hhplus.tdd.hhplusconcertjava.payment.domain.repository.PaymentRepository;
import io.hhplus.tdd.hhplusconcertjava.point.domain.entity.PointHistory;
import io.hhplus.tdd.hhplusconcertjava.user.domain.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PaymentServiceImpl implements PaymentService {
    PaymentRepository paymentRepository;
    ReservationRepository reservationRepository;

    @Transactional
    public Payment payReservation(User user, Reservation reservation, PointHistory pointHistory) {

        Payment payment = Payment.builder()
                .status(Payment.PaymentStatus.READY)
                .reservation(reservation)
                .pointHistory(pointHistory)
                .user(user)
                .build();

        payment.payReservation();

        payment = this.paymentRepository.save(payment);
        this.reservationRepository.save(reservation);

        return payment;

    }

}
