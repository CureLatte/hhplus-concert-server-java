package io.hhplus.tdd.hhplusconcertjava.payment.infrastructure.repository;

import io.hhplus.tdd.hhplusconcertjava.payment.domain.entity.Payment;
import io.hhplus.tdd.hhplusconcertjava.payment.domain.repository.PaymentRepository;
import io.hhplus.tdd.hhplusconcertjava.payment.infrastructure.entity.PaymentEntity;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PaymentJpaRepository implements PaymentRepository {
    IPaymentJpaRepository jpaRepository;


    @Override
    public Payment save(Payment payment) {
        PaymentEntity entity = this.jpaRepository.save(PaymentEntity.fromDomain(payment));

        return entity.toDomain();
    }
}
