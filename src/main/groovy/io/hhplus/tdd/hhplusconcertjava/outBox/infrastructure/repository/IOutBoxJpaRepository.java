package io.hhplus.tdd.hhplusconcertjava.outBox.infrastructure.repository;

import io.hhplus.tdd.hhplusconcertjava.outBox.infrastructure.entity.OutBoxEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IOutBoxJpaRepository extends JpaRepository<OutBoxEntity, Long> {

}
