package io.hhplus.tdd.hhplusconcertjava.concert.infrastructure.repository;

import io.hhplus.tdd.hhplusconcertjava.concert.infrastructure.entity.ConcertSeatEntity;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Hidden
public interface IConcertSeatJpaRepository extends JpaRepository<ConcertSeatEntity, Long> {

    @Query(value = """
        select * 
        from concert_seat 
        where concert_time_id= :concertTimeId
            and status = 'EMPTY'
    
    """,
    nativeQuery = true)
    @Lock(LockModeType.PESSIMISTIC_READ)
    public List<ConcertSeatEntity> findAllByAvailableSeat(@Param("concertTimeId") Long concertTimeId);
}
