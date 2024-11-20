package io.hhplus.tdd.hhplusconcertjava.payment.infrastructure;

import io.hhplus.tdd.hhplusconcertjava.payment.domain.entity.Payment;
import io.hhplus.tdd.hhplusconcertjava.payment.domain.event.PaymentEventPublisher;
import io.hhplus.tdd.hhplusconcertjava.payment.domain.event.SendOrderInfoEvent;
import io.hhplus.tdd.hhplusconcertjava.wait.domain.entity.DeleteActivateTokenEvent;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PaymentSpringEventPublisher implements PaymentEventPublisher {

    ApplicationEventPublisher publisher;

    @Override
    public void deleteActivateToken(String uuid) {
        this.publisher.publishEvent(DeleteActivateTokenEvent.builder().uuid(uuid).build());

    }

    @Override
    public void sendOrderInfo(Payment payment) {
        this.publisher.publishEvent(SendOrderInfoEvent.builder().payment(payment).build());
    }



}
