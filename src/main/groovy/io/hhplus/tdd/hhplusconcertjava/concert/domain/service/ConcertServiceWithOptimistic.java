package io.hhplus.tdd.hhplusconcertjava.concert.domain.service;

import io.hhplus.tdd.hhplusconcertjava.common.error.BusinessError;
import io.hhplus.tdd.hhplusconcertjava.common.error.ErrorCode;
import io.hhplus.tdd.hhplusconcertjava.concert.domain.entity.ConcertSeat;
import io.hhplus.tdd.hhplusconcertjava.concert.domain.entity.ConcertTime;
import io.hhplus.tdd.hhplusconcertjava.concert.domain.entity.Reservation;
import io.hhplus.tdd.hhplusconcertjava.concert.domain.repository.ConcertRepository;
import io.hhplus.tdd.hhplusconcertjava.concert.domain.repository.ConcertSeatRepository;
import io.hhplus.tdd.hhplusconcertjava.concert.domain.repository.ConcertTimeRepository;
import io.hhplus.tdd.hhplusconcertjava.concert.domain.repository.ReservationRepository;
import io.hhplus.tdd.hhplusconcertjava.user.domain.entity.User;
import io.hhplus.tdd.hhplusconcertjava.user.domain.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConcertServiceWithOptimistic extends ConcertService{
    public ConcertServiceWithOptimistic(ConcertRepository concertRepository, ConcertTimeRepository concertTimeRepository, ConcertSeatRepository concertSeatRepository, ReservationRepository reservationRepository, UserRepository userRepository) {
        super(concertRepository, concertTimeRepository, concertSeatRepository, reservationRepository, userRepository);
    }


    @Override
    public Reservation reserveV2(Long concertSeatId, Long userId, String uuid) {

        ConcertSeat concertSeat = this.concertSeatRepository.findByIdForShare(concertSeatId);

        concertSeat.reservation(uuid);
        this.concertSeatRepository.save(concertSeat);
        ConcertTime concertTime = concertSeat.getConcertTime();
        concertTime.decreaseLeftCnt();

        this.concertTimeRepository.save(concertSeat.getConcertTime());

        User user = this.userRepository.findById(userId);

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
