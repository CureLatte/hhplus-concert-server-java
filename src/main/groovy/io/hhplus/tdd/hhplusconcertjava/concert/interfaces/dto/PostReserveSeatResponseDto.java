package io.hhplus.tdd.hhplusconcertjava.concert.interfaces.dto;

public record PostReserveSeatResponseDto(
       ReservationDto reservation
) {

    public record ReservationDto(
            Long id,
            String status,
            String seatNumber,
            Long concertId
    ){

    }

}
