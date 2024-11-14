package io.hhplus.tdd.hhplusconcertjava.integration.wait;

import io.hhplus.tdd.hhplusconcertjava.integration.TestBaseIntegration;
import io.hhplus.tdd.hhplusconcertjava.wait.application.DeleteActivateTokenEvent;
import io.hhplus.tdd.hhplusconcertjava.wait.domain.entity.ActivateToken;
import io.hhplus.tdd.hhplusconcertjava.wait.domain.repository.ActivateTokenRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TestWaitEvent {

    private static final Logger log = LoggerFactory.getLogger(TestWaitEvent.class);

    @Nested
    public class TestDeleteActivateTokenEvent extends TestBaseIntegration{

        @Autowired
        ApplicationEventPublisher applicationEventPublisher;

        @Autowired
        ActivateTokenRepository activateTokenRepository;

        @Test
        public void 이벤트_호출_확인(){
            // GIVEN
            String uuid = UUID.randomUUID().toString();

            ActivateToken preActivateToken = this.activateTokenRepository.create(uuid);
            log.info("preActivateToken: {}",preActivateToken);

            // WHEN

            this.applicationEventPublisher.publishEvent(DeleteActivateTokenEvent.builder().uuid(uuid).build());


            // THEN
            ActivateToken activateToken = this.activateTokenRepository.get(uuid);
            assertNull(activateToken);

        }

    }

}
