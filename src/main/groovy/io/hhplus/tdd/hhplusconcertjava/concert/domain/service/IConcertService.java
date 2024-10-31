package io.hhplus.tdd.hhplusconcertjava.concert.domain.service;

import io.hhplus.tdd.hhplusconcertjava.concert.domain.entity.Concert;
import io.hhplus.tdd.hhplusconcertjava.concert.domain.entity.ConcertSeat;
import io.hhplus.tdd.hhplusconcertjava.concert.domain.entity.ConcertTime;
import io.hhplus.tdd.hhplusconcertjava.concert.domain.entity.Reservation;
import io.hhplus.tdd.hhplusconcertjava.user.domain.entity.User;

import java.util.List;

public interface IConcertService {
    public Concert getConcert(Long concertId);

    public ConcertTime getConcertTime(Long concertTimeId);

    public ConcertSeat getConcertSeat(Long concertSeatId);

    public Reservation getReservation(Long reservationId);

    public List<ConcertTime> getConcertTimes(Concert concert);

    public List<ConcertSeat> getConcertSeats(ConcertTime concertTime);

    // 예약하기
    public Reservation reserve(Concert concert, ConcertTime concertTime, ConcertSeat concertSeat, User user, String uuid);
    // 예약하기 + 비관적 Lock
    public Reservation reserveWithPessimistic(Long concertSeatId, Long userId, String uuid);
    // 예약하기 + 낙관적 Lock
    public Reservation reserveWithOptimistic(Long concertSeatId, Long userId, String uuid);
}
