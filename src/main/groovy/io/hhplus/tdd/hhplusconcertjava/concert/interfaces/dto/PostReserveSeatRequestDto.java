package io.hhplus.tdd.hhplusconcertjava.concert.interfaces.dto;


public record PostReserveSeatRequestDto(
        int concertId,
        int concertTimeId,
        int concertSeatId
) {
}
