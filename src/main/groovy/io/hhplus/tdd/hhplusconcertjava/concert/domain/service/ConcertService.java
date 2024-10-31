package io.hhplus.tdd.hhplusconcertjava.concert.domain.service;

import io.hhplus.tdd.hhplusconcertjava.common.error.BusinessError;
import io.hhplus.tdd.hhplusconcertjava.common.error.ErrorCode;
import io.hhplus.tdd.hhplusconcertjava.concert.domain.entity.Concert;
import io.hhplus.tdd.hhplusconcertjava.concert.domain.entity.ConcertSeat;
import io.hhplus.tdd.hhplusconcertjava.concert.domain.entity.ConcertTime;
import io.hhplus.tdd.hhplusconcertjava.concert.domain.entity.Reservation;
import io.hhplus.tdd.hhplusconcertjava.concert.domain.repository.ConcertRepository;
import io.hhplus.tdd.hhplusconcertjava.concert.domain.repository.ConcertSeatRepository;
import io.hhplus.tdd.hhplusconcertjava.concert.domain.repository.ConcertTimeRepository;
import io.hhplus.tdd.hhplusconcertjava.concert.domain.repository.ReservationRepository;
import io.hhplus.tdd.hhplusconcertjava.concert.infrastructure.entity.ConcertTimeEntity;
import io.hhplus.tdd.hhplusconcertjava.user.domain.entity.User;
import io.hhplus.tdd.hhplusconcertjava.user.domain.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor(onConstructor=@__(@Autowired))
public class ConcertService implements IConcertService {

    ConcertRepository concertRepository;
    ConcertTimeRepository concertTimeRepository;
    ConcertSeatRepository concertSeatRepository;
    ReservationRepository reservationRepository;
    UserRepository userRepository;



    @Override
    public Concert getConcert(Long concertId) {
        Concert concert = this.concertRepository.findById(concertId);

        if(concert == null) {
            throw new BusinessError(ErrorCode.NOT_FOUND_CONCERT_ERROR.getStatus(), ErrorCode.NOT_FOUND_CONCERT_ERROR.getMessage());
        }

        return concert;
    }

    @Override
    public ConcertTime getConcertTime(Long concertTimeId) {

        ConcertTime concertTime = this.concertTimeRepository.findById(concertTimeId);

        if(concertTime == null) {
            throw new BusinessError(ErrorCode.NOT_FOUND_CONCERT_TIME_ERROR.getStatus(), ErrorCode.NOT_FOUND_CONCERT_TIME_ERROR.getMessage());
        }


        return concertTime;
    }

    @Override
    public ConcertSeat getConcertSeat(Long concertSeatId) {

        ConcertSeat concertSeat = this.concertSeatRepository.findById(concertSeatId);

        if(concertSeat == null) {
            throw new BusinessError(ErrorCode.NOT_FOUND_CONCERT_SEAT_ERROR.getStatus(), ErrorCode.NOT_FOUND_CONCERT_SEAT_ERROR.getMessage());
        }

        return concertSeat;
    }

    @Override
    public Reservation getReservation(Long reservationId) {
        Reservation reservation = this.reservationRepository.findById(reservationId);

        if(reservation == null){
            throw new BusinessError(ErrorCode.NOT_FOUND_RESERVATION_ERROR.getStatus(), ErrorCode.NOT_FOUND_RESERVATION_ERROR.getMessage());
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

        // 중복 check ---> lock!!!
        Reservation duplicateReservation = this.reservationRepository.duplicateCheck(dummyReservation);
        if(duplicateReservation != null){
            throw new BusinessError(ErrorCode.DUPLICATION_RESERVATION_ERROR.getStatus(), ErrorCode.DUPLICATION_RESERVATION_ERROR.getMessage());
        }

        return this.reservationRepository.save(dummyReservation);
    }

    @Transactional
    public Reservation reserveV2(Long concertSeatId, Long userId,  String uuid){

        ConcertSeat concertSeat = this.concertSeatRepository.findByIdForUpdate(concertSeatId);

        concertSeat.reservation(uuid);
        this.concertSeatRepository.save(concertSeat);
        ConcertTime concertTime = concertSeat.getConcertTime();
        concertTime.decreaseLeftCnt();

        this.concertTimeRepository.save(concertSeat.getConcertTime());

        User user = this.userRepository.findByIdForUpdate(userId);

        Reservation dummyReservation = Reservation.builder()
                .id(0L)
                .status(Reservation.ReservationStatus.RESERVATION)
                .concertSeat(concertSeat)
                .concertTime(concertSeat.concertTime)
                .build();

        Reservation duplicateReservation = this.reservationRepository.duplicateCheck(dummyReservation);
        if(duplicateReservation != null){
            throw new BusinessError(ErrorCode.DUPLICATION_RESERVATION_ERROR.getStatus(), ErrorCode.DUPLICATION_RESERVATION_ERROR.getMessage());
        }

        dummyReservation.setUser(user);
        dummyReservation.setConcert(concertSeat.concertTime.concert);

        return this.reservationRepository.save(dummyReservation);
    }

}
