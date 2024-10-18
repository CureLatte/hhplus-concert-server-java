package io.hhplus.tdd.hhplusconcertjava.concert.domain.service;

import io.hhplus.tdd.hhplusconcertjava.common.BusinessError;
import io.hhplus.tdd.hhplusconcertjava.concert.domain.entity.Concert;
import io.hhplus.tdd.hhplusconcertjava.concert.domain.entity.ConcertSeat;
import io.hhplus.tdd.hhplusconcertjava.concert.domain.entity.ConcertTime;
import io.hhplus.tdd.hhplusconcertjava.concert.domain.entity.Reservation;
import io.hhplus.tdd.hhplusconcertjava.concert.domain.repository.ConcertRepository;
import io.hhplus.tdd.hhplusconcertjava.concert.domain.repository.ConcertSeatRepository;
import io.hhplus.tdd.hhplusconcertjava.concert.domain.repository.ConcertTimeRepository;
import io.hhplus.tdd.hhplusconcertjava.concert.domain.repository.ReservationRepository;
import io.hhplus.tdd.hhplusconcertjava.user.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@AllArgsConstructor(onConstructor=@__(@Autowired))
public class ConcertService implements IConcertService {

    ConcertRepository concertRepository;
    ConcertTimeRepository concertTimeRepository;
    ConcertSeatRepository concertSeatRepository;
    ReservationRepository reservationRepository;

    final public String NOT_FOUND_CONCERT_ERROR_MESSAGE = "존재하지 않은 콘서트 입니다.";
    final public String NOT_FOUND_CONCERT_TIME_ERROR_MESSAGE = "존재하지 않은 콘서트 날짜 입니다.";
    final public String NOT_FOUND_CONCERT_SEAT_ERROR_MESSAGE = "존재하지 않은 콘서트 좌석 입니다.";
    final public String NOT_FOUND_RESERVATION_ERROR_MESSAGE = "존재하지 않은 예약 입니다.";
    final public String DUPLICATION_RESERVATION_ERROR_MESSAGE = "이미 신청한 예약입니다.";



    @Override
    public Concert getConcert(Long concertId) {
        Concert concert = this.concertRepository.findById(concertId);

        if(concert == null) {
            throw new BusinessError(400, this.NOT_FOUND_CONCERT_ERROR_MESSAGE);
        }

        return concert;
    }

    @Override
    public ConcertTime getConcertTime(Long concertTimeId) {

        ConcertTime concertTime = this.concertTimeRepository.findById(concertTimeId);

        if(concertTime == null) {
            throw new BusinessError(400, this.NOT_FOUND_CONCERT_TIME_ERROR_MESSAGE);
        }


        return concertTime;
    }

    @Override
    public ConcertSeat getConcertSeat(Long concertSeatId) {

        ConcertSeat concertSeat = this.concertSeatRepository.findById(concertSeatId);

        if(concertSeat == null) {
            throw new BusinessError(400, this.NOT_FOUND_CONCERT_SEAT_ERROR_MESSAGE);
        }

        System.out.println("\nget concertSeat"+ concertSeat);
        return concertSeat;
    }

    @Override
    public Reservation getReservation(Long reservationId) {
        Reservation reservation = this.reservationRepository.findById(reservationId);

        if(reservation == null){
            throw new BusinessError(400, this.NOT_FOUND_RESERVATION_ERROR_MESSAGE);
        }

        return reservation;
    }

    @Override
    public List<ConcertTime> getConcertTimes(Concert concert) {
        return this.concertTimeRepository.findAllAvailableTime(concert);
    }

    @Override
    public List<ConcertSeat> getConcertSeats(ConcertTime concertTime) {
        return this.concertSeatRepository.findAllByAvailableSeat(concertTime);
    }

    @Override
    @Transactional
    public Reservation reserve(Concert concert, ConcertTime concertTime, ConcertSeat concertSeat, User user, String uuid) {

        // 남은 좌석 업데이트
        concertTime.decreaseLeftCnt();

        this.concertTimeRepository.save(concertTime);


        // 좌석 업데이트 uuid, status
        concertSeat.lock();
        this.concertSeatRepository.save(concertSeat);

        concertSeat.reservation(uuid);
        this.concertSeatRepository.save(concertSeat);


        // reservation 생성
        Reservation dummyReservation = Reservation.builder()
                .status(Reservation.ReservationStatus.RESERVATION)
                .concertSeat(concertSeat)
                .concertTime(concertTime)
                .concert(concert)
                .user(user)
                .build();


        dummyReservation.expireDateSetting();

        System.out.println("\ndummyReservation:  "+ dummyReservation);

        // 중복 check
        Reservation duplicateReservation = this.reservationRepository.duplicateCheck(dummyReservation);
        if(duplicateReservation !=null){
            throw new BusinessError(400, this.DUPLICATION_RESERVATION_ERROR_MESSAGE);
        }

        return this.reservationRepository.save(dummyReservation);
    }


}
