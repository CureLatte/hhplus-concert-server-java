package io.hhplus.tdd.hhplusconcertjava.payment.interfaces;

import io.hhplus.tdd.hhplusconcertjava.common.dto.ResponseDto;
import io.hhplus.tdd.hhplusconcertjava.payment.apllication.PaymentFacade;
import io.hhplus.tdd.hhplusconcertjava.payment.interfaces.dto.PostPayReservationRequestDto;
import io.hhplus.tdd.hhplusconcertjava.payment.interfaces.dto.PostPayReservationResponseDto;
import io.swagger.v3.oas.annotations.headers.Header;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PaymentController implements IPaymentController{

    PaymentFacade paymentFacade;

    @Override
    @PostMapping("/pay/reservation")
    public PostPayReservationResponseDto postPayReservation(@RequestHeader Map<String, String> headers, @RequestBody PostPayReservationRequestDto postPayReservationRequestDto) {

        String uuid = headers.get("token");


        return this.paymentFacade.payReservation(postPayReservationRequestDto.userId(), postPayReservationRequestDto.reservationId(), postPayReservationRequestDto.payAmount(), uuid);
    }
}
