package io.hhplus.tdd.hhplusconcertjava.unit.concert;

import io.hhplus.tdd.hhplusconcertjava.concert.domain.entity.ConcertSeat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class TestConcertSeat {

    @Test
    public void 선점_상태_표기__성공(){
        // GIVEN
        ConcertSeat concertSeat = ConcertSeat.builder().build();


        // WHEN
        concertSeat.lock();

        // THEN

        assertEquals(concertSeat.status, ConcertSeat.ConcertSeatStatus.PROCESS);
    }

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
}
