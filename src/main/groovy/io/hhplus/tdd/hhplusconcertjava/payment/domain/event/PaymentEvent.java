package io.hhplus.tdd.hhplusconcertjava.payment.domain.event;

import io.hhplus.tdd.hhplusconcertjava.payment.domain.entity.Payment;
import io.hhplus.tdd.hhplusconcertjava.point.domain.entity.PointHistory;

public interface PaymentEvent {
    public void payDoneEvent(String uuid);

    public void sendOrderInfo(Payment payment);

}
