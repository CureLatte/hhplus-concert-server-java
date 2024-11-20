package io.hhplus.tdd.hhplusconcertjava.payment.domain.event;

import io.hhplus.tdd.hhplusconcertjava.payment.domain.entity.Payment;

public interface PaymentEventPublisher {
    public void deleteActivateToken(String uuid);

    public void sendOrderInfo(Payment payment);

}
