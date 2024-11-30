package io.hhplus.tdd.hhplusconcertjava.integration.outbox;

import io.hhplus.tdd.hhplusconcertjava.integration.TestBaseIntegration;
import io.hhplus.tdd.hhplusconcertjava.outBox.domain.domain.OutBox;
import io.hhplus.tdd.hhplusconcertjava.outBox.domain.repository.OutBoxRepository;
import io.hhplus.tdd.hhplusconcertjava.outBox.domain.service.OutBoxService;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Transactional
public class TestOutBoxService extends TestBaseIntegration {

    @Autowired
    OutBoxService outBoxService;

    @Autowired
    OutBoxRepository outBoxRepository;


    @Nested
    class TestFindById{

        @Test
        public void 조회_확인__성공(){
            // GIVEN

            OutBox outBox = outBoxRepository.save(OutBox.builder()
                    .topic("testTopic")
                    .payload("testPayload")
                    .eventKey("testKey")
                    .status(OutBox.OutBoxStatus.INIT)
                    .build());

            // WHEN
            OutBox getOutBox = outBoxService.findById(outBox.getId());

            // THEN
            assertEquals(outBox.getId(), getOutBox.getId());

        }


        @Test
        public void 수신_변경__성공(){
            // GIVEN


            OutBox outBox = outBoxRepository.save(OutBox.builder()
                    .topic("testTopic")
                    .payload("testPayload")
                    .eventKey("testKey")
                    .status(OutBox.OutBoxStatus.INIT)
                    .build());

            // WHEN
            OutBox getOutBox = outBoxService.receive(outBox);

            // THEN
            OutBox checkOutBox = outBoxRepository.findById(getOutBox.getId());
            assertEquals(OutBox.OutBoxStatus.RECEIVE, getOutBox.getStatus());

        }




    }
}
