package io.hhplus.tdd.hhplusconcertjava.payment.domain.repository;

import io.hhplus.tdd.hhplusconcertjava.payment.domain.entity.Payment;

public interface PaymentRepository {
    public Payment save(Payment payment);
}
