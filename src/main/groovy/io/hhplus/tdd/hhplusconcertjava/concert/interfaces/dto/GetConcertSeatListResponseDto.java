package io.hhplus.tdd.hhplusconcertjava.concert.interfaces.dto;


import java.util.List;




public record GetConcertSeatListResponseDto(
        List<ConcertSeat> seat

) {
    public record ConcertSeat(
            Long concertSeatId,
            String number

    ){
    }

}
