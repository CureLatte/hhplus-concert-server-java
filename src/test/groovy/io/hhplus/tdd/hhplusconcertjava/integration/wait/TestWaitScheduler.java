package io.hhplus.tdd.hhplusconcertjava.integration.wait;

import io.hhplus.tdd.hhplusconcertjava.integration.TestBaseIntegration;
import io.hhplus.tdd.hhplusconcertjava.wait.application.WaitScheduler;
import io.hhplus.tdd.hhplusconcertjava.wait.domain.entity.WaitQueue;
import io.hhplus.tdd.hhplusconcertjava.wait.domain.repository.WaitQueueRepository;
import io.hhplus.tdd.hhplusconcertjava.wait.domain.service.WaitService;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestWaitScheduler {

    @Nested
    @Transactional
    class TestUpdateProcessCnt extends TestBaseIntegration{

        @Autowired
        WaitScheduler waitScheduler;

        @Autowired
        WaitQueueRepository waitQueueRepository;

        @Test
        public void 최대_인원_미만_생성시_상태_변경_성공(){
            // GIVEN
            WaitQueue defaultQueue = WaitQueue.builder().build();

            int testUserCnt = defaultQueue.getMaxCnt() - 1;

            for(int i=0; i<testUserCnt; i++){
                this.waitQueueRepository.create();
            }



            // WHEN
            waitScheduler.updateProcessCnt();

            // THEN
            Integer processCnt = this.waitQueueRepository.countProcess();


            assertEquals(testUserCnt, processCnt);

        }

        @Test
        public void 최대_인원_이상_생성시_작업_인원_유지_성공(){
            // GIVEN
            WaitQueue defaultQueue = WaitQueue.builder().build();

            int testUserCnt = defaultQueue.getMaxCnt() + 10;

            for(int i=0; i<testUserCnt; i++){
                this.waitQueueRepository.create();
            }



            // WHEN
            waitScheduler.updateProcessCnt();

            // THEN
            Integer processCnt = this.waitQueueRepository.countProcess();


            assertEquals(defaultQueue.getMaxCnt(), processCnt);

        }


        @Test
        public void 작업_완료시_제거_성공(){
            // GIVEN
            WaitQueue defaultQueue = WaitQueue.builder().build();

            int testUserCnt = defaultQueue.getMaxCnt();

            int updateCnt = 0;

            for(int i=0; i<testUserCnt; i++){
                WaitQueue waitQueue = this.waitQueueRepository.create();

                // update
                double dValue = Math.random();
                int randomNum = (int)(dValue * 10);
                if(randomNum >=5){
                    waitQueue.setStatus(WaitQueue.WaitStatus.FINISH);
                    updateCnt += 1;

                    this.waitQueueRepository.save(waitQueue);
                }
            }

            // WHEN
            waitScheduler.updateProcessCnt();

            // THEN
            Integer processCnt = this.waitQueueRepository.countProcess();


            assertEquals(testUserCnt - updateCnt, processCnt);

        }


        @Test
        public void 작업_인원_충원_확인_성공(){
            // GIVEN
            WaitQueue defaultQueue = WaitQueue.builder().build();
            int addCnt = 10;

            int testUserCnt = defaultQueue.getMaxCnt() - addCnt;


            for(int i=0; i<testUserCnt; i++){
                WaitQueue waitQueue = this.waitQueueRepository.create();
                waitQueue.setStatus(WaitQueue.WaitStatus.PROCESS);
                this.waitQueueRepository.save(waitQueue);
            }

            for(int i=0; i<addCnt; i++){
                this.waitQueueRepository.create();
            }



            // WHEN
            waitScheduler.updateProcessCnt();


            // THEN
            Integer processCnt = this.waitQueueRepository.countProcess();


            assertEquals(defaultQueue.getMaxCnt(), processCnt);

        }

    }


}
