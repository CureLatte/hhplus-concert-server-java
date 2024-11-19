package io.hhplus.tdd.hhplusconcertjava.point.infrastructure.repository;

import io.hhplus.tdd.hhplusconcertjava.point.domain.entity.PointHistory;
import io.hhplus.tdd.hhplusconcertjava.point.domain.repository.PointHistoryRepository;
import io.hhplus.tdd.hhplusconcertjava.point.infrastructure.entity.PointHistoryEntity;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PointHistoryJpaRepository implements PointHistoryRepository {
    IPointHistoryJpaRepository jpaRepository;

    @Override
    public List<PointHistory> findByStatus(PointHistory.PointStatus status) {

        List<PointHistoryEntity> pointHistoryEntityList = this.jpaRepository.findByStatus(status.name());

        if(pointHistoryEntityList.isEmpty()) return null;

        return pointHistoryEntityList.stream().map(PointHistoryEntity::toDomain).toList();
    }

    @Override
    public PointHistory save(PointHistory pointHistory) {
        PointHistoryEntity pointHistoryEntity = this.jpaRepository.save(PointHistoryEntity.fromDomain(pointHistory));
        return pointHistoryEntity.toDomain();
    }

    @Override
    public void delete(PointHistory pointHistory) {
        this.jpaRepository.delete(PointHistoryEntity.fromDomain(pointHistory));
    }
}
