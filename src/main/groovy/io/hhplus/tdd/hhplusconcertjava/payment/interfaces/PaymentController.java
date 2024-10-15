package io.hhplus.tdd.hhplusconcertjava.payment.interfaces;

import io.hhplus.tdd.hhplusconcertjava.payment.interfaces.dto.PostPayReservationRequestDto;
import io.hhplus.tdd.hhplusconcertjava.payment.interfaces.dto.PostPayReservationResponseDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class PaymentController implements IPaymentController{

    @Override
    @PostMapping("/pay/reservation")
    public PostPayReservationResponseDto postPayReservation(@RequestBody PostPayReservationRequestDto postPayReservationRequestDto) {
        return new PostPayReservationResponseDto(true);
    }
}
