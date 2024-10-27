package io.hhplus.tdd.hhplusconcertjava.payment.interfaces;

import io.hhplus.tdd.hhplusconcertjava.common.dto.ResponseDto;
import io.hhplus.tdd.hhplusconcertjava.payment.apllication.PaymentFacade;
import io.hhplus.tdd.hhplusconcertjava.payment.interfaces.dto.PostPayReservationRequestDto;
import io.hhplus.tdd.hhplusconcertjava.payment.interfaces.dto.PostPayReservationResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PaymentController implements IPaymentController{

    PaymentFacade paymentFacade;

    @Override
    @PostMapping("/pay/reservation")
    public PostPayReservationResponseDto postPayReservation(@RequestBody PostPayReservationRequestDto postPayReservationRequestDto) {
        return this.paymentFacade.payReservation(postPayReservationRequestDto.userId(), postPayReservationRequestDto.reservationId(), postPayReservationRequestDto.payAmount());
    }
}
