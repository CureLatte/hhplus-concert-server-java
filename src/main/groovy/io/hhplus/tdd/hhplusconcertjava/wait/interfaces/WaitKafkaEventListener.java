package io.hhplus.tdd.hhplusconcertjava.wait.interfaces;

import io.hhplus.tdd.hhplusconcertjava.wait.domain.service.WaitService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class WaitKafkaEventListener {
    WaitService waitService;

    @KafkaListener(topics = "deactivateToken")
    public void deleteActivateToken(ConsumerRecord<String, String> record) {

        String uuid = record.value();

        log.info("Received deactivate token event: {}", uuid);

        this.waitService.deleteActivateToken(uuid);

    }

}
