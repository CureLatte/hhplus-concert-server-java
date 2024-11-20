package io.hhplus.tdd.hhplusconcertjava.payment.interfaces;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PaymentKafkaEventListener {
    @KafkaListener( topics="newTopic")
    public void test(ConsumerRecord<String, String> data) {

        log.info("PaymentKafkaListener");
       log.info("data: {}", data);
        log.info("______");
    }


    @KafkaListener(topics="payment")
    public void sendPayment(ConsumerRecord<String, String> data) {
        log.info("sendPayment: {}", data);

    }
}
