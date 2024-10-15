package io.hhplus.tdd.hhplusconcertjava.payment.interfaces;

import io.hhplus.tdd.hhplusconcertjava.payment.interfaces.dto.PostPayReservationRequestDto;
import io.hhplus.tdd.hhplusconcertjava.payment.interfaces.dto.PostPayReservationResponseDto;

public interface IPaymentController {
    public PostPayReservationResponseDto postPayReservation(PostPayReservationRequestDto postPayReservationRequestDto);

}
