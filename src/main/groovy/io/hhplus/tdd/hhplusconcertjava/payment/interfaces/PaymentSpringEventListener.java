package io.hhplus.tdd.hhplusconcertjava.payment.interfaces;

import io.hhplus.tdd.hhplusconcertjava.outBox.domain.domain.OutBox;
import io.hhplus.tdd.hhplusconcertjava.payment.domain.event.SendOrderInfoEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import javax.swing.*;

@Slf4j
@Component
public class PaymentSpringEventListener {

    KafkaTemplate<String, String > kafkaTemplate;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void sendPaymentEvent(SendOrderInfoEvent sendOrderInfoEvent){
        log.info("Send order info: {}", sendOrderInfoEvent);

        OutBox outBox = sendOrderInfoEvent.getOutBox();

        kafkaTemplate.send(outBox.getTopic(), outBox.eventKey, outBox.topic);

    }

}
