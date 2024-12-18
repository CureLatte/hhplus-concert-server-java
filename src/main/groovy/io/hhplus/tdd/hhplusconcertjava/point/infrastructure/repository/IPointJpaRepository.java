package io.hhplus.tdd.hhplusconcertjava.point.infrastructure.repository;

import io.hhplus.tdd.hhplusconcertjava.point.infrastructure.entity.PointEntity;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
@Hidden
public interface IPointJpaRepository extends JpaRepository<PointEntity, Long> {

    @Query(value = """
            select * 
            from point
            where user_id= :userId
            limit 1
        """,
        nativeQuery = true)
    public PointEntity findByUserId(@Param("userId") Long userId);
}
