package io.hhplus.tdd.hhplusconcertjava.payment.domain.event;

import io.hhplus.tdd.hhplusconcertjava.payment.domain.entity.Payment;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SendOrderInfoEvent {
    private Payment payment;
}