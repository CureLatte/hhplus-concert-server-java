package io.hhplus.tdd.hhplusconcertjava.integration.payment;

import io.hhplus.tdd.hhplusconcertjava.concert.domain.entity.*;
import io.hhplus.tdd.hhplusconcertjava.concert.domain.repository.ConcertRepository;
import io.hhplus.tdd.hhplusconcertjava.concert.domain.repository.ConcertSeatRepository;
import io.hhplus.tdd.hhplusconcertjava.concert.domain.repository.ConcertTimeRepository;
import io.hhplus.tdd.hhplusconcertjava.concert.domain.repository.ReservationRepository;
import io.hhplus.tdd.hhplusconcertjava.integration.TestBaseIntegration;
import io.hhplus.tdd.hhplusconcertjava.payment.apllication.PaymentFacade;
import io.hhplus.tdd.hhplusconcertjava.payment.domain.event.SendOrderInfoEvent;
import io.hhplus.tdd.hhplusconcertjava.payment.interfaces.PaymentSpringEventListener;
import io.hhplus.tdd.hhplusconcertjava.point.domain.entity.Point;
import io.hhplus.tdd.hhplusconcertjava.point.domain.entity.PointHistory;
import io.hhplus.tdd.hhplusconcertjava.point.domain.repository.PointHistoryRepository;
import io.hhplus.tdd.hhplusconcertjava.point.domain.repository.PointRepository;
import io.hhplus.tdd.hhplusconcertjava.user.domain.entity.User;
import io.hhplus.tdd.hhplusconcertjava.user.domain.repository.UserRepository;
import io.hhplus.tdd.hhplusconcertjava.wait.domain.entity.DeleteActivateTokenEvent;
import io.hhplus.tdd.hhplusconcertjava.wait.interfaces.WaitEventListener;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TestPaymentFacade {

    @Transactional
    @Nested
    @ExtendWith(MockitoExtension.class)
    class TestPayReservation extends TestBaseIntegration {


        @Autowired
        ConcertRepository concertRepository;
        @Autowired
        ConcertSeatRepository concertSeatRepository;
        @Autowired
        ConcertTimeRepository concertTimeRepository;
        @Autowired
        UserRepository userRepository;
        @Autowired
        ReservationRepository reservationRepository;

        @Autowired
        PointRepository pointRepository;

        @Autowired
        PointHistoryRepository pointHistoryRepository;

        @Autowired
        PaymentFacade paymentFacade;

        @MockBean
        PaymentSpringEventListener paymentEventListener;

        @MockBean
        WaitEventListener waitEventListener;


        @BeforeEach
        public void setUp(){
            // db reset
            this.pointRepository.deleteAll();
            this.reservationRepository.clearTable();
            this.concertRepository.deleteAll();
            this.concertTimeRepository.deleteAll();
            this.concertSeatRepository.deleteAll();

        }

        @Test
        public void 에약_결제_하기__성공(){

            // GIVEN
            User user = this.userRepository.create(User.builder()
                            .name("testUser")
                    .build());

            Concert concert = this.concertRepository.create(Concert.builder()
                            .status(Concert.ConcertStatus.OPEN)
                            .title("testTitle")
                    .build());

            int concertPrice = 1000;
            int concertSeatMaxCnt = 20;
            int concertSeatLeftCnt = 10;

            ConcertTime concertTime = this.concertTimeRepository.save(ConcertTime.builder()
                            .status(ConcertTime.ConcertTimeStatus.ON_SALE)
                            .leftCnt(concertSeatLeftCnt)
                            .maxCnt(concertSeatMaxCnt)
                            .startTime(LocalDateTime.now())
                            .endTime(LocalDateTime.now().plusHours(2))
                            .price(concertPrice)
                            .concert(concert)
                    .build());

            List<ConcertSeat> concertSeatList = new ArrayList<ConcertSeat>();

            for(int i=0;i<concertSeatMaxCnt;i++){

                ConcertSeat.ConcertSeatStatus status = ConcertSeat.ConcertSeatStatus.EMPTY;

                if(i > concertSeatLeftCnt){
                    status = ConcertSeat.ConcertSeatStatus.RESERVATION;
                }


                concertSeatList.add(this.concertSeatRepository.save(ConcertSeat.builder()
                                .status(status)
                                .number("A" + i )
                                .concertTime(concertTime)
                        .build()));

            }

            Reservation reservation = this.reservationRepository.save(Reservation.builder()
                            .concert(concert)
                            .concertTime(concertTime)
                            .concertSeat(concertSeatList.get(0))
                            .user(user)
                            .status(Reservation.ReservationStatus.RESERVATION)
                    .build());

            // Point 충전
            Point point = this.pointRepository.save(Point.builder()
                            .balance(concertPrice)
                            .user(user)
                    .build());

            // History create
            PointHistory pointHistory = this.pointHistoryRepository.save(PointHistory.builder()
                            .point(point)
                            .pointAmount(concertPrice)
                            .status(PointHistory.PointStatus.CHARGE)
                    .build());


            // WHEN
            this.paymentFacade.payReservation(user.getId(), reservation.getId(), concertPrice, UUID.randomUUID().toString());

            // THEN
            // 예약 상태 확인
            Reservation paidReservation = this.reservationRepository.findById(reservation.getId());

            assertEquals(Reservation.ReservationStatus.PAY_DONE, paidReservation.getStatus());


            // 포인트 확인
            Point leftPoint = this.pointRepository.findByUser(user);

            assertEquals(0, leftPoint.balance);

            // 포인트 로그 확인
            List<PointHistory> updatedPointHistoryList = this.pointHistoryRepository.findByStatus(PointHistory.PointStatus.USE);

            assertEquals(updatedPointHistoryList.size(), 1);
            assertEquals(updatedPointHistoryList.get(0).pointAmount, concertPrice);
        }



        @Test
        @Rollback(true)
        public void 롤백시_이벤트_호출안됨__확인(){

            // GIVEN
            String uuid = UUID.randomUUID().toString();

            User user = this.userRepository.create(User.builder()
                            .name("testUser")
                    .build());

            Point point = this.pointRepository.save(Point.builder()
                            .user(user)
                            .balance(3000)
                    .build());

            Concert concert = this.concertRepository.create(Concert.builder()
                            .status(Concert.ConcertStatus.OPEN)
                            .title("testTitle")
                            .viewRank(ConcertViewRank.builder()
                                    .limitAge(15)
                                    .build())
                    .build());

            ConcertTime concertTime = this.concertTimeRepository.save(ConcertTime.builder()
                            .concert(concert)
                            .status(ConcertTime.ConcertTimeStatus.ON_SALE)
                            .leftCnt(30)
                            .maxCnt(30)
                            .startTime(LocalDateTime.now())
                            .endTime(LocalDateTime.now().plusHours(2))
                            .price(3000)
                    .build());


            ConcertSeat concertSeat = this.concertSeatRepository.save(ConcertSeat.builder()
                            .concertTime(concertTime)
                            .number("seat_1")
                            .status(ConcertSeat.ConcertSeatStatus.RESERVATION)
                            .uuid(uuid)
                    .build());

            Reservation reservation = this.reservationRepository.save(Reservation.builder()
                            .status(Reservation.ReservationStatus.RESERVATION)
                            .concert(concert)
                            .concertSeat(concertSeat)
                            .concertTime(concertTime)
                            .user(user)

                    .build());

            // WHEN
            this.paymentFacade.payReservation(user.id, reservation.getId(), 3000, uuid);

            // THEN
            verify(this.paymentEventListener, never()).sendPaymentEvent(any(SendOrderInfoEvent.class));
            verify(this.waitEventListener, never()).deleteActivateToken(any(DeleteActivateTokenEvent.class));
        }


        @Test
        @Rollback(false)
        public void 커밋시_이벤트_호출__확인(){

            // GIVEN
            String uuid = UUID.randomUUID().toString();

            User user = this.userRepository.create(User.builder()
                    .name("testUser")
                    .build());

            Point point = this.pointRepository.save(Point.builder()
                    .user(user)
                    .balance(3000)
                    .build());

            Concert concert = this.concertRepository.create(Concert.builder()
                    .status(Concert.ConcertStatus.OPEN)
                    .title("testTitle")
                    .viewRank(ConcertViewRank.builder()
                            .limitAge(15)
                            .build())
                    .build());

            ConcertTime concertTime = this.concertTimeRepository.save(ConcertTime.builder()
                    .concert(concert)
                    .status(ConcertTime.ConcertTimeStatus.ON_SALE)
                    .leftCnt(30)
                    .maxCnt(30)
                    .startTime(LocalDateTime.now())
                    .endTime(LocalDateTime.now().plusHours(2))
                    .price(3000)
                    .build());


            ConcertSeat concertSeat = this.concertSeatRepository.save(ConcertSeat.builder()
                    .concertTime(concertTime)
                    .number("seat_1")
                    .status(ConcertSeat.ConcertSeatStatus.RESERVATION)
                    .uuid(uuid)
                    .build());

            Reservation reservation = this.reservationRepository.save(Reservation.builder()
                    .status(Reservation.ReservationStatus.RESERVATION)
                    .concert(concert)
                    .concertSeat(concertSeat)
                    .concertTime(concertTime)
                    .user(user)

                    .build());

            // WHEN
            this.paymentFacade.payReservation(user.id, reservation.getId(), 3000, uuid);

            // THEN
            verify(this.paymentEventListener).sendPaymentEvent(any(SendOrderInfoEvent.class));
            // verify(this.waitEventListener).deleteActivateToken(any());
        }

    }



}
