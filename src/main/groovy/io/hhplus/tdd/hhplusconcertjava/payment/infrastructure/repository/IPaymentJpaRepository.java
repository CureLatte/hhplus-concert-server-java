package io.hhplus.tdd.hhplusconcertjava.payment.infrastructure.repository;

import io.hhplus.tdd.hhplusconcertjava.payment.infrastructure.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPaymentJpaRepository extends JpaRepository<PaymentEntity, Long> {
}
