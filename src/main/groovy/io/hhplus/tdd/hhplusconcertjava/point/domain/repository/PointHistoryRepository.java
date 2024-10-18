package io.hhplus.tdd.hhplusconcertjava.point.domain.repository;

import io.hhplus.tdd.hhplusconcertjava.point.domain.entity.PointHistory;

public interface PointHistoryRepository {
    public PointHistory save(PointHistory pointHistory);
}
