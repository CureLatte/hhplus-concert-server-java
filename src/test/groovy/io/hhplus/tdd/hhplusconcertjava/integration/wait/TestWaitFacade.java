package io.hhplus.tdd.hhplusconcertjava.integration.wait;

import io.hhplus.tdd.hhplusconcertjava.integration.TestBaseIntegration;
import io.hhplus.tdd.hhplusconcertjava.wait.application.WaitFacade;
import io.hhplus.tdd.hhplusconcertjava.wait.application.WaitScheduler;
import io.hhplus.tdd.hhplusconcertjava.wait.domain.entity.WaitQueue;
import io.hhplus.tdd.hhplusconcertjava.wait.domain.repository.WaitQueueRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestWaitFacade {

    @Nested
    @Transactional
    class TestGetWaitToken extends TestBaseIntegration {

        @Autowired
        WaitFacade waitFacade;

        @Autowired
        WaitScheduler waitScheduler;

        @Autowired
        WaitQueueRepository waitQueueRepository;

        @BeforeEach
        public void DBReset(){
            waitQueueRepository.clearTable();
        }

        @Test
        public void 유저_토큰_생성_조회__성공(){
            // GIVEN

            // WHEN
            WaitQueue waitQueue = this.waitFacade.getWaitQueue(null, null);

            // THEN
            assertEquals(waitQueue.getStatus(), WaitQueue.WaitStatus.WAIT);

        }

        @Test
        public void 유저_토큰_재_조회시__성공(){
            // GIVEN
            WaitQueue waitQueue = this.waitQueueRepository.create();


            // WHEN
            WaitQueue checkWaitQueue = this.waitFacade.getWaitQueue(waitQueue.getUuid(), null);

            // THEN
            assertEquals(checkWaitQueue.getStatus(), WaitQueue.WaitStatus.WAIT);

        }

        @Test
        public void 유저_토큰_대기열_통과_후_재_조회시__성공(){
            // GIVEN
            WaitQueue waitQueue = this.waitQueueRepository.create();

            waitScheduler.updateProcessCnt();

            // WHEN
            WaitQueue checkWaitQueue = this.waitFacade.getWaitQueue(waitQueue.getUuid(), null);

            // THEN

            Integer processCnt = this.waitQueueRepository.countProcess();

            assertEquals(1, processCnt);
            assertEquals(checkWaitQueue.getStatus(), WaitQueue.WaitStatus.PROCESS);

        }


    }

}
