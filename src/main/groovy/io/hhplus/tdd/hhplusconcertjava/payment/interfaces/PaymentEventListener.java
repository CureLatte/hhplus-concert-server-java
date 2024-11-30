package io.hhplus.tdd.hhplusconcertjava.payment.interfaces;

import io.hhplus.tdd.hhplusconcertjava.payment.domain.entity.Payment;

public interface PaymentEventListener {
    public void sendPaymentEvent();
}
