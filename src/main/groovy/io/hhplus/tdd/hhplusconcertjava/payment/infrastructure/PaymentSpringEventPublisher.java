package io.hhplus.tdd.hhplusconcertjava.payment.infrastructure;

import io.hhplus.tdd.hhplusconcertjava.outBox.domain.domain.OutBox;
import io.hhplus.tdd.hhplusconcertjava.payment.domain.event.PaymentEventPublisher;
import io.hhplus.tdd.hhplusconcertjava.payment.domain.event.SendOrderInfoEvent;
import io.hhplus.tdd.hhplusconcertjava.wait.domain.event.DeleteActivateTokenEvent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PaymentSpringEventPublisher implements PaymentEventPublisher {

    ApplicationEventPublisher publisher;

    @Override
    public void deleteActivateToken(OutBox outBox) {
        // spring event
        this.publisher.publishEvent(DeleteActivateTokenEvent.builder().outBox(outBox).build());

    }

    @Override
    public void sendOrderInfo(OutBox outBox) {
        log.info("sendOrderInfo event publish!: {}", outBox);

        // spring event
        this.publisher.publishEvent(SendOrderInfoEvent.builder().outBox(outBox).build());
    }



}
