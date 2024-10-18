package io.hhplus.tdd.hhplusconcertjava.point.infrastructure.repository;

import io.hhplus.tdd.hhplusconcertjava.point.infrastructure.entity.PointEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IPointJpaRepository extends JpaRepository<PointEntity, Long> {

    @Query(value = """
            select * 
            from point
            where user_id= :userId
        """,
        nativeQuery = true)
    public PointEntity findByUserId(@Param("userId") Long userId);
}
