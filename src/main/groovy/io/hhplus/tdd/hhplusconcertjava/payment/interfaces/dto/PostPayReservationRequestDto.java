package io.hhplus.tdd.hhplusconcertjava.payment.interfaces.dto;

public record PostPayReservationRequestDto(
        Long userId,
        Long reservationId,
        int payAmount
) {
}
