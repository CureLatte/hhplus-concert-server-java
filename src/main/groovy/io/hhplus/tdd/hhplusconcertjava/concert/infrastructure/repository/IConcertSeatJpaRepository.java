package io.hhplus.tdd.hhplusconcertjava.concert.infrastructure.repository;

import io.hhplus.tdd.hhplusconcertjava.concert.infrastructure.entity.ConcertSeatEntity;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Hidden
public interface IConcertSeatJpaRepository extends JpaRepository<ConcertSeatEntity, Long> {

    @Transactional
    @Query(value = """
        select * 
        from concert_seat 
        where concert_time_id= :concertTimeId
            and status = 'EMPTY' for update
    
    """,
    nativeQuery = true)
    public List<ConcertSeatEntity> findAllByAvailableSeat(@Param("concertTimeId") Long concertTimeId);

    @Query(value = """
        delete from concert_seat
    """,
    nativeQuery = true)
    @Modifying
    @Transactional
    public void clearTable();

    @Query(value= """
        select s from ConcertSeatEntity s where s.id= :concertSeatId
    """)
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value = "10000")})
    public ConcertSeatEntity findByIdForUpdate(@Param("concertSeatId") Long concertSeatId);


    @Query(value = """
        select s from ConcertSeatEntity s where s.id= :concertSeatId
    """)
    @Lock(LockModeType.OPTIMISTIC)
    public ConcertSeatEntity findByIdForShare(@Param("concertSeatId") Long concertSeatId);

}
