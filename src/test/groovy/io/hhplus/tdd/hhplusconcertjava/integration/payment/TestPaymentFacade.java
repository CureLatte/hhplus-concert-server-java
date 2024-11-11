package io.hhplus.tdd.hhplusconcertjava.integration.payment;

import io.hhplus.tdd.hhplusconcertjava.concert.domain.entity.Concert;
import io.hhplus.tdd.hhplusconcertjava.concert.domain.entity.ConcertSeat;
import io.hhplus.tdd.hhplusconcertjava.concert.domain.entity.ConcertTime;
import io.hhplus.tdd.hhplusconcertjava.concert.domain.entity.Reservation;
import io.hhplus.tdd.hhplusconcertjava.concert.domain.repository.ConcertRepository;
import io.hhplus.tdd.hhplusconcertjava.concert.domain.repository.ConcertSeatRepository;
import io.hhplus.tdd.hhplusconcertjava.concert.domain.repository.ConcertTimeRepository;
import io.hhplus.tdd.hhplusconcertjava.concert.domain.repository.ReservationRepository;
import io.hhplus.tdd.hhplusconcertjava.concert.infrastructure.repository.IConcertJpaRepository;
import io.hhplus.tdd.hhplusconcertjava.concert.infrastructure.repository.IConcertSeatJpaRepository;
import io.hhplus.tdd.hhplusconcertjava.concert.infrastructure.repository.IConcertTimeJpaRepository;
import io.hhplus.tdd.hhplusconcertjava.integration.TestBaseIntegration;
import io.hhplus.tdd.hhplusconcertjava.payment.apllication.PaymentFacade;
import io.hhplus.tdd.hhplusconcertjava.payment.domain.entity.Payment;
import io.hhplus.tdd.hhplusconcertjava.point.domain.entity.Point;
import io.hhplus.tdd.hhplusconcertjava.point.domain.entity.PointHistory;
import io.hhplus.tdd.hhplusconcertjava.point.domain.repository.PointHistoryRepository;
import io.hhplus.tdd.hhplusconcertjava.point.domain.repository.PointRepository;
import io.hhplus.tdd.hhplusconcertjava.user.domain.entity.User;
import io.hhplus.tdd.hhplusconcertjava.user.domain.repository.UserRepository;
import io.hhplus.tdd.hhplusconcertjava.user.infrastructure.IUserJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestPaymentFacade {

    @Transactional
    @Nested
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


    }
}
