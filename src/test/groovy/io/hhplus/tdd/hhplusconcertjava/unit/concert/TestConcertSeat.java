package io.hhplus.tdd.hhplusconcertjava.unit.concert;

import io.hhplus.tdd.hhplusconcertjava.common.BusinessError;
import io.hhplus.tdd.hhplusconcertjava.concert.domain.entity.ConcertSeat;
import io.hhplus.tdd.hhplusconcertjava.concert.domain.entity.ConcertTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class TestConcertSeat {


    @Test
    public void 예약_설정_하기__성공(){
        // GIVEN
        ConcertSeat concertSeat = ConcertSeat.builder().build();
        String uuid = UUID.randomUUID().toString();

        // WHEN
        concertSeat.reservation(uuid);

        // THEN
        assertEquals(concertSeat.status, ConcertSeat.ConcertSeatStatus.RESERVATION);
        assertEquals(concertSeat.uuid, uuid);

    }

    @Test
    public void 이미_좌석_예약_한_경우__에러(){
        // GIVEN
        ConcertSeat concertSeat = ConcertSeat.builder()
                .status(ConcertSeat.ConcertSeatStatus.RESERVATION)
                .concertTime(ConcertTime.builder().status(ConcertTime.ConcertTimeStatus.ON_SALE).build())
                .build();
        String uuid = UUID.randomUUID().toString();

        // WHEN
        BusinessError error = assertThrows(BusinessError.class, () -> concertSeat.reservation(uuid));

        // THEN
        assertEquals(error.message, concertSeat.ALREADY_RESERVATION_ERROR_MESSAGE);
    }

    @Test
    public void 해당_날짜가_매진_된_경우__에러(){
        // GIVEN
        ConcertSeat concertSeat = ConcertSeat.builder()
                .status(ConcertSeat.ConcertSeatStatus.RESERVATION)
                .concertTime(ConcertTime.builder().status(ConcertTime.ConcertTimeStatus.SOLD_OUT).build())
                .build();
        String uuid = UUID.randomUUID().toString();

        // WHEN
        BusinessError error = assertThrows(BusinessError.class, () -> concertSeat.reservation(uuid));

        // THEN
        assertEquals(error.message, concertSeat.CONCERT_TIME_SOLE_OUT_ERROR_MESSAGE);
    }
}
