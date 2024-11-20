package io.hhplus.tdd.hhplusconcertjava.payment.interfaces;

import io.hhplus.tdd.hhplusconcertjava.payment.domain.event.SendOrderInfoEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
public class PaymentSpringEventListener {

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    // @EventListener(classes = SendOrderInfoEvent.class)
    public void sendPaymentEvent(SendOrderInfoEvent sendOrderInfoEvent){
        log.info("Send order info: {}", sendOrderInfoEvent);
    }

}
