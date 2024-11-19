package io.hhplus.tdd.hhplusconcertjava.point.domain.repository;

import io.hhplus.tdd.hhplusconcertjava.point.domain.entity.PointHistory;

import java.util.List;

public interface PointHistoryRepository {

    public List<PointHistory> findByStatus(PointHistory.PointStatus status);
    public PointHistory save(PointHistory pointHistory);

    public void delete(PointHistory pointHistory);
}
