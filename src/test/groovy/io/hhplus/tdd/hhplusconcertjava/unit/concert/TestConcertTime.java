package io.hhplus.tdd.hhplusconcertjava.unit.concert;

import io.hhplus.tdd.hhplusconcertjava.common.error.BusinessError;
import io.hhplus.tdd.hhplusconcertjava.common.error.ErrorCode;
import io.hhplus.tdd.hhplusconcertjava.concert.domain.entity.ConcertTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class TestConcertTime {

    @Test
    public void 남은_자리_줄이기__성공(){
        // GIVEN
        int leftCnt = 30;
        ConcertTime concertTime = ConcertTime.builder()
                .leftCnt(leftCnt).build();


        // WHEN
        concertTime.decreaseLeftCnt();

        // THEN
        assertEquals(leftCnt-1, concertTime.getLeftCnt());
    }

    @Test
    public void 남은_좌석_없을_경우__에러(){
        // GIVEN
        int leftCnt =0;
        ConcertTime concertTime = ConcertTime.builder()
                .leftCnt(leftCnt).build();

        // WHEN
        BusinessError error = assertThrows(BusinessError.class, () -> concertTime.decreaseLeftCnt());

        // THEN
        assertEquals(ErrorCode.LEFT_CNT_DOES_NOT_EXIST_ERROR.getStatus(), error.status);
        assertEquals(ErrorCode.LEFT_CNT_DOES_NOT_EXIST_ERROR.getMessage(), error.message);
    }
}
