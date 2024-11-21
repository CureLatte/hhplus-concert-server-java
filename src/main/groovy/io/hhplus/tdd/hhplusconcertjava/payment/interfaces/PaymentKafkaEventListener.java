package io.hhplus.tdd.hhplusconcertjava.payment.interfaces;

import io.hhplus.tdd.hhplusconcertjava.common.error.BusinessError;
import io.hhplus.tdd.hhplusconcertjava.outBox.domain.domain.OutBox;
import io.hhplus.tdd.hhplusconcertjava.outBox.domain.service.OutBoxService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PaymentKafkaEventListener {
    OutBoxService outBoxService;

    @KafkaListener( topics="newTopic")
    public void test(ConsumerRecord<String, String> data) {
        log.info("PaymentKafkaListener");
       log.info("data: {}", data);
        log.info("______");
    }


    @KafkaListener(topics="payment")
    public void sendPayment(ConsumerRecord<String, String> data) {
        try {


            log.info("sendPayment: {}", data.value());

            OutBox outBox = outBoxService.findByOutBox(OutBox.builder()
                    .topic(data.topic())
                    .eventKey(data.key())
                    .payload(data.value())
                    .build());


            outBoxService.receive(outBox);


            // send API

            outBoxService.success(outBox);

        } catch (BusinessError businessError) {
            log.error(businessError.getMessage());
        }


    }
}
