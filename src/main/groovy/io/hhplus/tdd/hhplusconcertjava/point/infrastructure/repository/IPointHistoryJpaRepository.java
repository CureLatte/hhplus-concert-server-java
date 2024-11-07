package io.hhplus.tdd.hhplusconcertjava.point.infrastructure.repository;

import io.hhplus.tdd.hhplusconcertjava.point.domain.entity.PointHistory;
import io.hhplus.tdd.hhplusconcertjava.point.infrastructure.entity.PointHistoryEntity;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Hidden
public interface IPointHistoryJpaRepository extends JpaRepository<PointHistoryEntity, Long> {

    @Query(value = """
        select PE from PointHistoryEntity PE where PE.status = :statusName
    """)
    public List<PointHistoryEntity> findByStatus(@Param("statusName") String name);
}
