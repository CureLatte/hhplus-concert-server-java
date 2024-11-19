package io.hhplus.tdd.hhplusconcertjava.payment.interfaces;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PaymentKafkaListener {
    @KafkaListener(groupId = "test", topics="newTopic")
    public void test(ConsumerRecord<String, String> data) {
        log.info("PaymentKafkaListener");
       log.info("data: {}", data);
//        log.info("acknowledgment: {}", acknowledgment);
//        log.info("consumer: {}", consumer);
        log.info("______");
    }
}
