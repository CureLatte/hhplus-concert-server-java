package io.hhplus.tdd.hhplusconcertjava.integration.concert;

import io.hhplus.tdd.hhplusconcertjava.common.BusinessError;
import io.hhplus.tdd.hhplusconcertjava.concert.application.ConcertFacade;
import io.hhplus.tdd.hhplusconcertjava.concert.domain.entity.Concert;
import io.hhplus.tdd.hhplusconcertjava.concert.domain.entity.ConcertSeat;
import io.hhplus.tdd.hhplusconcertjava.concert.domain.entity.ConcertTime;
import io.hhplus.tdd.hhplusconcertjava.concert.domain.entity.Reservation;
import io.hhplus.tdd.hhplusconcertjava.concert.domain.repository.ConcertRepository;
import io.hhplus.tdd.hhplusconcertjava.concert.domain.repository.ConcertSeatRepository;
import io.hhplus.tdd.hhplusconcertjava.concert.domain.repository.ConcertTimeRepository;
import io.hhplus.tdd.hhplusconcertjava.concert.domain.repository.ReservationRepository;
import io.hhplus.tdd.hhplusconcertjava.concert.interfaces.dto.GetConcertSeatListResponseDto;
import io.hhplus.tdd.hhplusconcertjava.concert.interfaces.dto.GetConcertTimeResponseDto;
import io.hhplus.tdd.hhplusconcertjava.concert.interfaces.dto.PostReserveSeatRequestDto;
import io.hhplus.tdd.hhplusconcertjava.concert.interfaces.dto.PostReserveSeatResponseDto;
import io.hhplus.tdd.hhplusconcertjava.integration.TestBaseIntegration;
import io.hhplus.tdd.hhplusconcertjava.user.domain.entity.User;
import io.hhplus.tdd.hhplusconcertjava.user.domain.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestConcertFacade {

    @Nested
    @Transactional
    class TestGetConcertTimeList extends TestBaseIntegration {
        @Autowired
        ConcertFacade concertFacade;

        @Autowired
        ConcertRepository concertRepository;

        @Autowired
        ConcertTimeRepository concertTimeRepository;

        @Test
        public void 콘서트_시간_조회__성공(){
            // GIVEN
            Concert concert = this.concertRepository.save(Concert.builder()
                    .status(Concert.ConcertStatus.OPEN)
                    .title("test")
                    .build());


            LocalDateTime now = LocalDateTime.now();

            ConcertTime concertTime = this.concertTimeRepository.save( ConcertTime.builder()
                    .concert(concert)
                    .startTime(now.plusDays(1))
                    .endTime(now.plusDays(2).plusHours(2))
                    .leftCnt(30)
                    .maxCnt(30)
                    .status(ConcertTime.ConcertTimeStatus.ON_SALE)
                    .price(1000)
                    .build());


            // WHEN
            GetConcertTimeResponseDto responseDto = concertFacade.getConcertTimeList(concert.getId());


            // THEN
            assertEquals(1, responseDto.date().size());

        }


        @Test
        public void 잔여_좌석이_없는_콘서트_시간_조회__미조회(){
            // GIVEN
            Concert concert = this.concertRepository.save(Concert.builder()
                    .status(Concert.ConcertStatus.OPEN)
                    .title("test")
                    .build());


            LocalDateTime now = LocalDateTime.now();

            ConcertTime concertTime = this.concertTimeRepository.save( ConcertTime.builder()
                    .concert(concert)
                    .startTime(now.plusDays(1))
                    .endTime(now.plusDays(2).plusHours(2))
                    .leftCnt(0)
                    .maxCnt(30)
                    .status(ConcertTime.ConcertTimeStatus.ON_SALE)
                    .price(1000)
                    .build());


            // WHEN
            GetConcertTimeResponseDto responseDto = concertFacade.getConcertTimeList(concert.getId());


            // THEN
            assertEquals(0, responseDto.date().size());


        }


        @Test
        public void 매진_상태_콘서트_시간_조회__미조회(){
            // GIVEN
            Concert concert = this.concertRepository.save(Concert.builder()
                    .status(Concert.ConcertStatus.OPEN)
                    .title("test")
                    .build());


            LocalDateTime now = LocalDateTime.now();

            ConcertTime concertTime = this.concertTimeRepository.save( ConcertTime.builder()
                    .concert(concert)
                    .startTime(now.plusDays(1))
                    .endTime(now.plusDays(2).plusHours(2))
                    .leftCnt(30)
                    .maxCnt(30)
                    .status(ConcertTime.ConcertTimeStatus.SOLD_OUT)
                    .price(1000)
                    .build());


            // WHEN
            GetConcertTimeResponseDto responseDto = concertFacade.getConcertTimeList(concert.getId());


            // THEN
            assertEquals(0, responseDto.date().size());

        }
    }

    @Nested
    @Transactional
    class TestGetConcertSeatList extends TestBaseIntegration {
        @Autowired
        ConcertFacade concertFacade;

        @Autowired
        ConcertRepository concertRepository;

        @Autowired
        ConcertTimeRepository concertTimeRepository;

        @Autowired
        ConcertSeatRepository concertSeatRepository;

        @Test
        public void 콘서트_잔여_좌석_조회__성공(){
            // GIVEN
            Concert concert = this.concertRepository.save(Concert.builder()
                            .title("test")
                            .status(Concert.ConcertStatus.OPEN)
                            .build());

            ConcertTime concertTime = this.concertTimeRepository.save(ConcertTime.builder()
                            .concert(concert)
                            .startTime(LocalDateTime.now())
                            .endTime(LocalDateTime.now().plusDays(1))
                            .price(1000)
                            .maxCnt(30)
                            .leftCnt(30)
                            .status(ConcertTime.ConcertTimeStatus.ON_SALE)
                    .build());

            ConcertSeat emptyConcertSeat1 = this.concertSeatRepository.save(ConcertSeat.builder()

                            .concertTime(concertTime)
                            .number("1")
                            .status(ConcertSeat.ConcertSeatStatus.EMPTY)
                    .build());

            ConcertSeat emptyConcertSeat2 = this.concertSeatRepository.save(ConcertSeat.builder()

                    .concertTime(concertTime)
                    .number("2")
                    .status(ConcertSeat.ConcertSeatStatus.EMPTY)
                    .build());


            // WHEN
            GetConcertSeatListResponseDto responseDto = concertFacade.getConcertSeatList(concertTime.getId());


            // THEN
            assertEquals(2, responseDto.seat().size());
        }


        @Test
        public void 콘서트_잔여_좌석만_조회__성공(){
            // GIVEN
            Concert concert = this.concertRepository.save(Concert.builder()
                    .title("test")
                    .status(Concert.ConcertStatus.OPEN)
                    .build());

            ConcertTime concertTime = this.concertTimeRepository.save(ConcertTime.builder()
                    .concert(concert)
                    .startTime(LocalDateTime.now())
                    .endTime(LocalDateTime.now().plusDays(1))
                    .price(1000)
                    .maxCnt(30)
                    .leftCnt(30)
                    .status(ConcertTime.ConcertTimeStatus.ON_SALE)
                    .build());

            ConcertSeat emptyConcertSeat1 = this.concertSeatRepository.save(ConcertSeat.builder()

                    .concertTime(concertTime)
                    .number("1")
                    .status(ConcertSeat.ConcertSeatStatus.PROCESS)
                    .build());

            ConcertSeat emptyConcertSeat2 = this.concertSeatRepository.save(ConcertSeat.builder()

                    .concertTime(concertTime)
                    .number("2")
                    .status(ConcertSeat.ConcertSeatStatus.RESERVATION)
                    .build());


            // WHEN
            GetConcertSeatListResponseDto responseDto = concertFacade.getConcertSeatList(concertTime.getId());


            // THEN
            assertEquals(0, responseDto.seat().size());
        }


    }


    @Nested
    @Transactional
    class TestPostReserveSeat extends TestBaseIntegration {

        @Autowired
        ConcertFacade concertFacade;

        @Autowired
        UserRepository userRepository;

        @Autowired
        ConcertRepository concertRepository;

        @Autowired
        ConcertTimeRepository concertTimeRepository;

        @Autowired
        ConcertSeatRepository concertSeatRepository;

        @Autowired
        ReservationRepository reservationRepository;

        @Test
        public void 좌석_예약__성공(){
            // GIVEN
            User user = this.userRepository.save(User.builder().name("testUser1").build());


            Concert concert = this.concertRepository.save(Concert.builder()
                    .title("test")
                    .status(Concert.ConcertStatus.OPEN)
                    .build());

            ConcertTime concertTime = this.concertTimeRepository.save(ConcertTime.builder()
                    .concert(concert)
                    .startTime(LocalDateTime.now())
                    .endTime(LocalDateTime.now().plusDays(1))
                    .price(1000)
                    .maxCnt(30)
                    .leftCnt(30)
                    .status(ConcertTime.ConcertTimeStatus.ON_SALE)
                    .build());

            ConcertSeat concertSeat = this.concertSeatRepository.save(ConcertSeat.builder()
                    .concertTime(concertTime)
                    .number("1")
                    .status(ConcertSeat.ConcertSeatStatus.EMPTY)
                    .build());







            // WHEN
            PostReserveSeatResponseDto responseDto = this.concertFacade.postReserveSeat(concert.id, concertTime.id, concertSeat.id, UUID.randomUUID().toString(), user.id);


            // THEN
            Long reservationId = responseDto.reservation().id();
            Reservation reservation = this.reservationRepository.findById(reservationId);



            assertEquals(Reservation.ReservationStatus.RESERVATION, reservation.getStatus());



        }

        @Test
        public void 이미_예약한_좌석_일_경우__에러(){
            // GIVEN
            User user = this.userRepository.save(User.builder().name("testUser1").build());


            Concert concert = this.concertRepository.save(Concert.builder()
                    .title("test")
                    .status(Concert.ConcertStatus.OPEN)
                    .build());

            ConcertTime concertTime = this.concertTimeRepository.save(ConcertTime.builder()
                    .concert(concert)
                    .startTime(LocalDateTime.now())
                    .endTime(LocalDateTime.now().plusDays(1))
                    .price(1000)
                    .maxCnt(30)
                    .leftCnt(30)
                    .status(ConcertTime.ConcertTimeStatus.ON_SALE)
                    .build());

            ConcertSeat concertSeat = this.concertSeatRepository.save(ConcertSeat.builder()
                    .concertTime(concertTime)
                    .number("1")
                            .uuid(UUID.randomUUID().toString())
                    .status(ConcertSeat.ConcertSeatStatus.RESERVATION)
                    .build());

            // WHEN
            BusinessError error = assertThrows(BusinessError.class, ()-> {
               this.concertFacade.postReserveSeat(concert.id, concertTime.id, concertSeat.id, UUID.randomUUID().toString(), user.id);
            });

            // THEN
            assertEquals(error.status, 400);
            assertEquals(error.message, concertSeat.ALREADY_RESERVATION_ERROR_MESSAGE);
        }

        @Test
        public void 이미_매진된_시간_예약_시__에러(){
            // GIVEN
            User user = this.userRepository.save(User.builder().name("testUser1").build());


            Concert concert = this.concertRepository.save(Concert.builder()
                    .title("test")
                    .status(Concert.ConcertStatus.OPEN)
                    .build());

            ConcertTime concertTime = this.concertTimeRepository.save(ConcertTime.builder()
                    .concert(concert)
                    .startTime(LocalDateTime.now())
                    .endTime(LocalDateTime.now().plusDays(1))
                    .price(1000)
                    .maxCnt(30)
                    .leftCnt(30)
                    .status(ConcertTime.ConcertTimeStatus.SOLD_OUT)
                    .build());

            ConcertSeat concertSeat = this.concertSeatRepository.save(ConcertSeat.builder()
                    .concertTime(concertTime)
                    .number("1")
                    .uuid(UUID.randomUUID().toString())
                    .status(ConcertSeat.ConcertSeatStatus.RESERVATION)
                    .build());

            // WHEN
            BusinessError error = assertThrows(BusinessError.class, ()-> {
                this.concertFacade.postReserveSeat(concert.id, concertTime.id, concertSeat.id, UUID.randomUUID().toString(), user.id);
            });

            // THEN
            assertEquals(error.status, 400);
            assertEquals(error.message, concertSeat.CONCERT_TIME_SOLE_OUT_ERROR_MESSAGE);
        }


    }


    @Nested
    class TestConcurrency extends TestBaseIntegration{

        @Autowired
        ConcertFacade concertFacade;

        @Autowired
        UserRepository userRepository;

        @Autowired
        ConcertRepository concertRepository;

        @Autowired
        ConcertTimeRepository concertTimeRepository;

        @Autowired
        ConcertSeatRepository concertSeatRepository;

        @Autowired
        ReservationRepository reservationRepository;

        long userCnt = 5;
        List<User> userList;
        Concert concert;
        ConcertTime concertTime;
        ConcertSeat concertSeat;

        @BeforeEach
        public void settingDB(){
            List<User> userList = new ArrayList<>();
            for(long i=0; i<this.userCnt; i++){
                userList.add(this.userRepository.save(User.builder().name("testUser" + i).build()));
            }
            this.userList =  userList;

            this.concert = this.concertRepository.save(Concert.builder()
                    .title("test")
                    .status(Concert.ConcertStatus.OPEN)
                    .build());

            this.concertTime = this.concertTimeRepository.save(ConcertTime.builder()
                    .concert(concert)
                    .startTime(LocalDateTime.now())
                    .endTime(LocalDateTime.now().plusDays(1))
                    .price(1000)
                    .maxCnt(30)
                    .leftCnt(30)
                    .status(ConcertTime.ConcertTimeStatus.ON_SALE)
                    .build());

            this.concertSeat = this.concertSeatRepository.save(ConcertSeat.builder()
                    .concertTime(concertTime)
                    .number("1")
                    .uuid(UUID.randomUUID().toString())
                    .status(ConcertSeat.ConcertSeatStatus.EMPTY)
                    .build());

        }

        @Test
        public void 동시_예약_진행한_경우__성공() throws InterruptedException {
            // GIVEN
            // DB SETTING




            Long userCnt = 40L;



            // Concurrency Setting
            CountDownLatch latch = new CountDownLatch(this.userList.size());
            ExecutorService executorService = Executors.newFixedThreadPool(userList.size());

            AtomicInteger successCnt = new AtomicInteger(0);
            AtomicInteger failCnt = new AtomicInteger(0);

            // WHEN
            for(User user : userList){
                System.out.println(user);

                executorService.execute(() -> {
                    try {
                        this.concertFacade.postReserveSeat(concert.id, concertTime.id, concertSeat.id, UUID.randomUUID().toString(), user.id);
                        successCnt.getAndIncrement();
                    } catch(BusinessError businessError){

                        System.out.println(businessError);
                        failCnt.getAndIncrement();
                    } finally {
                        latch.countDown();
                    }
                });


            }

            latch.await();

            System.out.println("SUCCESS CNT:" + successCnt);
            System.out.println("FAIL CNT:" + failCnt);

            // THEN

        }

    }
}
