package io.hhplus.tdd.hhplusconcertjava.point.infrastructure.repository;

import io.hhplus.tdd.hhplusconcertjava.point.infrastructure.entity.PointEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPointJpaRepository extends JpaRepository<PointEntity, Long> {
}
