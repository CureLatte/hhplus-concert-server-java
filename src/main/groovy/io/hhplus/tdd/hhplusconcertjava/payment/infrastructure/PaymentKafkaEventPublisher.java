package io.hhplus.tdd.hhplusconcertjava.payment.infrastructure;

import io.hhplus.tdd.hhplusconcertjava.payment.domain.entity.Payment;
import io.hhplus.tdd.hhplusconcertjava.payment.domain.event.PaymentEventPublisher;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PaymentKafkaEventPublisher implements PaymentEventPublisher {

    KafkaTemplate<String, String> kafkaTemplate;



    @Override
    public void deleteActivateToken(String uuid) {
        log.info("Delete activate token for uuid: {}", uuid);
        this.kafkaTemplate.send("deactivateToken", uuid, uuid);
    }

    @Override
    public void sendOrderInfo(Payment payment) {
        log.info("Send order info: {}", payment);
        this.kafkaTemplate.send("payment", payment.getId().toString(), payment.toString());
    }
}
