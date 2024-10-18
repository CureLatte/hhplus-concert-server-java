package io.hhplus.tdd.hhplusconcertjava.unit.concert;

import io.hhplus.tdd.hhplusconcertjava.common.BusinessError;
import io.hhplus.tdd.hhplusconcertjava.concert.domain.entity.Concert;
import io.hhplus.tdd.hhplusconcertjava.concert.domain.entity.ConcertSeat;
import io.hhplus.tdd.hhplusconcertjava.concert.domain.entity.ConcertTime;
import io.hhplus.tdd.hhplusconcertjava.concert.domain.entity.Reservation;
import io.hhplus.tdd.hhplusconcertjava.concert.domain.repository.ConcertRepository;
import io.hhplus.tdd.hhplusconcertjava.concert.domain.repository.ConcertSeatRepository;
import io.hhplus.tdd.hhplusconcertjava.concert.domain.repository.ConcertTimeRepository;
import io.hhplus.tdd.hhplusconcertjava.concert.domain.repository.ReservationRepository;
import io.hhplus.tdd.hhplusconcertjava.concert.domain.service.ConcertService;
import io.hhplus.tdd.hhplusconcertjava.user.domain.entity.User;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
public class TestConcertService {
    @InjectMocks
    ConcertService concertService;

    @Mock
    ConcertRepository concertRepository;

    @Mock
    ConcertSeatRepository concertSeatRepository;

    @Mock
    ConcertTimeRepository concertTimeRepository;

    @Mock
    ReservationRepository reservationRepository;



    @Nested
    class TestReserve{

        @Test
        public void 예약__성공(){
            // GIVEN
            User user = User.builder()
                    .id(1L)
                    .name("test_user")
                    .build();

            Concert concert = Concert.builder()
                    .title("IU CONCERT")
                    .status(Concert.ConcertStatus.OPEN)
                    .build();

            LocalDateTime now = LocalDateTime.now();

            int leftCnt = 30;

            ConcertTime concertTime = ConcertTime.builder()
                    .concert(concert)
                    .startTime(now.plusDays(1))
                    .endTime(now.plusDays(1).plusHours(1))
                    .status(ConcertTime.ConcertTimeStatus.ON_SALE)
                    .price(1000)
                    .maxCnt(30)
                    .leftCnt(leftCnt)
                    .build();

            ConcertSeat concertSeat = ConcertSeat.builder()
                    .concertTime(concertTime)
                    .status(ConcertSeat.ConcertSeatStatus.EMPTY)
                    .number("01")
                    .build();

            String uuid = UUID.randomUUID().toString();

            lenient()
                    .when(concertTimeRepository.save(concertTime))
                    .thenReturn(concertTime);

            lenient()
                    .when(concertSeatRepository.save(concertSeat))
                    .thenReturn(concertSeat);

            lenient()
                    .when(reservationRepository.duplicateCheck(any()))
                    .thenReturn(null);
            lenient()
                    .when(reservationRepository.save(any()))
                    .thenReturn(Reservation.builder().build());

            // WHEN
            concertService.reserve(concert, concertTime, concertSeat, user, uuid);


            // THEN
            assertEquals(ConcertSeat.ConcertSeatStatus.RESERVATION, concertSeat.getStatus());
            assertEquals(leftCnt -1 , concertTime.getLeftCnt());
            assertEquals(uuid, concertSeat.getUuid());

        }

        @Test
        public void 좌석_만석_예약_실패__에러(){
            // GIVEN
            User user = User.builder()
                    .id(1L)
                    .name("test_user")
                    .build();

            Concert concert = Concert.builder()
                    .title("IU CONCERT")
                    .status(Concert.ConcertStatus.OPEN)
                    .build();

            LocalDateTime now = LocalDateTime.now();

            int leftCnt = 0;

            ConcertTime concertTime = ConcertTime.builder()
                    .concert(concert)
                    .startTime(now.plusDays(1))
                    .endTime(now.plusDays(1).plusHours(1))
                    .status(ConcertTime.ConcertTimeStatus.ON_SALE)
                    .price(1000)
                    .maxCnt(30)
                    .leftCnt(leftCnt)
                    .build();

            ConcertSeat concertSeat = ConcertSeat.builder()
                    .concertTime(concertTime)
                    .status(ConcertSeat.ConcertSeatStatus.EMPTY)
                    .number("01")
                    .build();

            String uuid = UUID.randomUUID().toString();

            lenient()
                    .when(concertTimeRepository.save(concertTime))
                    .thenReturn(concertTime);

            lenient()
                    .when(concertSeatRepository.save(concertSeat))
                    .thenReturn(concertSeat);

            lenient()
                    .when(reservationRepository.duplicateCheck(any()))
                    .thenReturn(null);
            lenient()
                    .when(reservationRepository.save(any()))
                    .thenReturn(Reservation.builder().build());

            // WHEN
            BusinessError businessError = assertThrows(BusinessError.class, ()-> {
                concertService.reserve(concert, concertTime, concertSeat, user, uuid);
            });



            // THEN
            assertEquals(400, businessError.status);
            assertEquals(concertTime.LEFT_CNT_DOES_NOT_EXIST_ERROR_MESSAGE, businessError.message);

        }

    }
}
