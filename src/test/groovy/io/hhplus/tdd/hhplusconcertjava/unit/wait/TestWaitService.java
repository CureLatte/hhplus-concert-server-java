package io.hhplus.tdd.hhplusconcertjava.unit.wait;


import io.hhplus.tdd.hhplusconcertjava.wait.domain.entity.WaitQueue;
import io.hhplus.tdd.hhplusconcertjava.wait.domain.repository.WaitQueueRepository;
import io.hhplus.tdd.hhplusconcertjava.wait.domain.service.WaitService;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestWaitService {

    @InjectMocks
    WaitService waitService;

    @Mock
    WaitQueueRepository waitQueueRepository;


    @Nested
    class TestGetWaitQueue{


        @Test
        public void 대기열_조회_성공(){
            // GIVEN
            String uuid = UUID.randomUUID().toString();

            lenient()
                    .when(waitQueueRepository.findByUUID(uuid))
                    .thenReturn(WaitQueue.builder().uuid(uuid).build());

            // WHEN
            WaitQueue waitQueue = waitService.getWaitQueue(uuid);


            // THEN
            assertEquals(uuid, waitQueue.getUuid());
        }

        @Test
        public void 대기열_새로_등록_성공(){
            // GIVEN
            Long newId = 1L;

            lenient()
                    .when(waitQueueRepository.findByUUID(null))
                    .thenReturn(null);

            lenient()
                    .when(waitQueueRepository.create())
                    .thenReturn(WaitQueue.builder().id(newId).uuid(UUID.randomUUID().toString()).build());

            // WHEN
            WaitQueue waitQueue = waitService.getWaitQueue(null);

            // THEN
            assertEquals(waitQueue.getId(), newId);

        }

    }


    @Nested
    class TestUpdateProcessWaitQueue{

        @Test
        public void 작업_인원_만원시_스케줄러_작업_호출_확인(){
            // GIVEN
            WaitQueue defaultWaitQueue = WaitQueue.builder().build();

            lenient()
                    .when(waitQueueRepository.countProcess())
                            .thenReturn(Integer.valueOf(defaultWaitQueue.getMaxCnt()));

            // WHEN
            waitService.updateProcessWaitQueue();

            // THEN
            verify(waitQueueRepository, times(1)).deleteFinish();
            verify(waitQueueRepository, times(1)).deleteTimeout();
            verify(waitQueueRepository, times(1)).countProcess();
            verify(waitQueueRepository, never()).updateStatusOrderByCreatedAt(0);
        }


        @Test
        public void 작업_인원_부족시_스케줄러_작업_호출_확인(){
            // GIVEN
            Integer processCnt = Integer.valueOf(1);

            WaitQueue defaultWaitQueue = WaitQueue.builder().build();

            lenient()
                    .when(waitQueueRepository.countProcess())
                    .thenReturn(processCnt);

            // WHEN
            waitService.updateProcessWaitQueue();

            // THEN
            verify(waitQueueRepository, times(1)).deleteFinish();
            verify(waitQueueRepository, times(1)).deleteTimeout();
            verify(waitQueueRepository, times(1)).countProcess();
            verify(waitQueueRepository, times(1)).updateStatusOrderByCreatedAt(anyInt());
        }

    }

}
