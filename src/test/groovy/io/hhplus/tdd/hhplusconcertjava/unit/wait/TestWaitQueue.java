package io.hhplus.tdd.hhplusconcertjava.unit.wait;

import io.hhplus.tdd.hhplusconcertjava.common.error.BusinessError;
import io.hhplus.tdd.hhplusconcertjava.common.error.ErrorCode;
import io.hhplus.tdd.hhplusconcertjava.wait.domain.entity.WaitQueue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class TestWaitQueue {

    @Test
    public void 작업_가능시_토큰_상태_확인__성공(){
        // GIVEN
        WaitQueue waitQueue = WaitQueue.builder()
                .status(WaitQueue.WaitStatus.PROCESS)
                .build();


        // WHEN
        waitQueue.validate();

        // THEN


    }

    @Test
    public void 대기시_토큰_상태_확인__에러(){
        // GIVEN
        final WaitQueue waitQueue = WaitQueue.builder()
                .status(WaitQueue.WaitStatus.WAIT)
                .build();


        // WHEN
        BusinessError businessError = assertThrows(BusinessError.class, waitQueue::validate);

        // THEN
        assertEquals(ErrorCode.CHECK_PROCESS_ERROR.getStatus(), businessError.status);
        assertEquals(ErrorCode.CHECK_PROCESS_ERROR.getMessage(), businessError.message);

    }

    @Test
    public void 작업_완료시_토큰_상태_확인__에러(){
        // GIVEN
        final WaitQueue waitQueue = WaitQueue.builder()
                .status(WaitQueue.WaitStatus.FINISH)
                .build();


        // WHEN
        BusinessError businessError = assertThrows(BusinessError.class, waitQueue::validate);

        // THEN
        assertEquals(ErrorCode.CHECK_PROCESS_ERROR.getStatus(), businessError.status);
        assertEquals(ErrorCode.CHECK_PROCESS_ERROR.getMessage(), businessError.message);

    }

}
