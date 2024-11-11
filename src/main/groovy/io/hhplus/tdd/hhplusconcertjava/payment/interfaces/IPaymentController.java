package io.hhplus.tdd.hhplusconcertjava.payment.interfaces;

import io.hhplus.tdd.hhplusconcertjava.payment.interfaces.dto.PostPayReservationRequestDto;
import io.hhplus.tdd.hhplusconcertjava.payment.interfaces.dto.PostPayReservationResponseDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Map;

@Tag(name="결제 API", description = "결제 관련 API 입니다.")
public interface IPaymentController {
    public PostPayReservationResponseDto postPayReservation(@RequestHeader Map<String, String> headers, PostPayReservationRequestDto postPayReservationRequestDto);

}
