package io.hhplus.tdd.hhplusconcertjava.concert.interfaces.dto;

public record PostReserveSeatResponseDto(
       ReservationDto reservation
) {

    public record ReservationDto(
            int id,
            String status,
            int seatNumber,
            int concertId
    ){

    }

}
