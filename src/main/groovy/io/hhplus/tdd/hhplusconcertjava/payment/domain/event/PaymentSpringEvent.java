package io.hhplus.tdd.hhplusconcertjava.payment.domain.event;

import io.hhplus.tdd.hhplusconcertjava.payment.domain.entity.Payment;
import io.hhplus.tdd.hhplusconcertjava.point.domain.entity.PointHistory;
import io.hhplus.tdd.hhplusconcertjava.point.domain.entity.UseCancelEvent;
import io.hhplus.tdd.hhplusconcertjava.wait.domain.entity.DeleteActivateTokenEvent;
import org.springframework.boot.context.event.SpringApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class PaymentSpringEvent implements PaymentEvent{

    ApplicationEventPublisher publisher;

    @Override
    public void payDoneEvent(String uuid) {
        this.publisher.publishEvent(DeleteActivateTokenEvent.builder().uuid(uuid).build());

    }

    @Override
    public void sendOrderInfo(Payment payment) {
        this.publisher.publishEvent(SendOrderInfoEvent.builder().payment(payment).build());
    }



}
