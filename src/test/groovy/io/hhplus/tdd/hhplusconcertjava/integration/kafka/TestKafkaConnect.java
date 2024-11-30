package io.hhplus.tdd.hhplusconcertjava.integration.kafka;

import io.hhplus.tdd.hhplusconcertjava.integration.TestBaseIntegration;
import io.hhplus.tdd.hhplusconcertjava.payment.interfaces.PaymentKafkaEventListener;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.KafkaTemplate;

public class TestKafkaConnect extends TestBaseIntegration {

    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    @MockBean
    PaymentKafkaEventListener paymentKafkaListener;

    @Test
    public void 카프카_연결_테스트(){

        kafkaTemplate.send("newTopic", "hello THIS SEND2 OK?");
        kafkaTemplate.send("newTopic", "test22");

    }

}
