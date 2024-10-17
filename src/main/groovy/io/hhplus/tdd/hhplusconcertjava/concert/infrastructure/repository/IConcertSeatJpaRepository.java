package io.hhplus.tdd.hhplusconcertjava.concert.infrastructure.repository;

import io.hhplus.tdd.hhplusconcertjava.concert.infrastructure.entity.ConcertSeatEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IConcertSeatJpaRepository extends JpaRepository<ConcertSeatEntity, Long> {

    @Query(value = """
        select * 
        from concert_seat 
        where concert_time_id= :concertTimeId
            and status = 'EMPTY'
    
    """,
    nativeQuery = true)
    public List<ConcertSeatEntity> findAllByAvailableSeat(@Param("concertTimeId") Long concertTimeId);
}
