package io.hhplus.tdd.hhplusconcertjava.payment.infrastructure.repository;

import io.hhplus.tdd.hhplusconcertjava.payment.infrastructure.entity.PaymentEntity;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Hidden
public interface IPaymentJpaRepository extends JpaRepository<PaymentEntity, Long> {
}
