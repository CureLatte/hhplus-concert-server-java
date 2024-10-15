package io.hhplus.tdd.hhplusconcertjava.payment.interfaces.dto;

public record PostPayReservationRequestDto(
        int userId,
        int reservationId,
        int payAmount
) {
}
