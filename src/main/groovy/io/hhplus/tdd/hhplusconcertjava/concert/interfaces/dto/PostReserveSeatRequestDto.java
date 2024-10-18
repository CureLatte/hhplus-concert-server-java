package io.hhplus.tdd.hhplusconcertjava.concert.interfaces.dto;


import io.hhplus.tdd.hhplusconcertjava.user.domain.entity.User;

public record PostReserveSeatRequestDto(
        Long concertId,
        Long concertTimeId,
        Long concertSeatId
) {
    public static String uuid;
    public static User user;
}
