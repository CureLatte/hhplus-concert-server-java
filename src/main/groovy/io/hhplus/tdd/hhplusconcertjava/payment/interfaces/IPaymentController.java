package io.hhplus.tdd.hhplusconcertjava.payment.interfaces;

import io.hhplus.tdd.hhplusconcertjava.payment.interfaces.dto.PostPayReservationRequestDto;
import io.hhplus.tdd.hhplusconcertjava.payment.interfaces.dto.PostPayReservationResponseDto;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name="결제 API", description = "결제 관련 API 입니다.")
public interface IPaymentController {
    public PostPayReservationResponseDto postPayReservation(PostPayReservationRequestDto postPayReservationRequestDto);

}
