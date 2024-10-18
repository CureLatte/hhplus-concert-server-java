package io.hhplus.tdd.hhplusconcertjava.point.infrastructure.repository;

import io.hhplus.tdd.hhplusconcertjava.point.domain.repository.PointHistoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PointHistoryJpaRepository implements PointHistoryRepository {
    IPointJpaRepository jpaRepository;
}
