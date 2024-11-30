package io.hhplus.tdd.hhplusconcertjava.integration.outbox;

import io.hhplus.tdd.hhplusconcertjava.common.kafka.Topic;
import io.hhplus.tdd.hhplusconcertjava.integration.TestBaseIntegration;
import io.hhplus.tdd.hhplusconcertjava.outBox.application.OutBoxScheduler;
import io.hhplus.tdd.hhplusconcertjava.outBox.domain.domain.OutBox;
import io.hhplus.tdd.hhplusconcertjava.outBox.domain.repository.OutBoxRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestOutBoxScheduler {

    @Nested
    class TestOutBoxRePublish extends TestBaseIntegration {

        @Autowired
        OutBoxScheduler outBoxScheduler;

        @Autowired
        OutBoxRepository outBoxRepository;

        @Test
        public void 스케줄러_동작_확인(){
            // GIVEN
            LocalDateTime createdAt = LocalDateTime.now().minusMinutes(6);

            OutBox outBox = this.outBoxRepository.save(OutBox.builder()
                            .topic(Topic.PAYMENT_TOPIC)
                            .eventKey("payment_99")
                            .payload("paymentId: 99")
                            .status(OutBox.OutBoxStatus.INIT)
                            .createdAt(createdAt)
                            .updatedAt(createdAt)
                            .deletedAt(null)
                    .build());



            // WHEN
            this.outBoxScheduler.rePublish();

            // THEN
            outBox = this.outBoxRepository.findById(outBox.id);

            assertEquals(OutBox.OutBoxStatus.RECEIVE, outBox.getStatus());
        }

    }
}
