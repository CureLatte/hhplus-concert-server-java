package io.hhplus.tdd.hhplusconcertjava.payment.domain.event;

import io.hhplus.tdd.hhplusconcertjava.outBox.domain.domain.OutBox;
import io.hhplus.tdd.hhplusconcertjava.payment.domain.entity.Payment;

public interface PaymentEventPublisher {
    public void deleteActivateToken(OutBox outBox);

    public void sendOrderInfo(OutBox outBox);

}
