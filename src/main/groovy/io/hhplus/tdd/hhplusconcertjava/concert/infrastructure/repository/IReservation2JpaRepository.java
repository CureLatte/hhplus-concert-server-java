package io.hhplus.tdd.hhplusconcertjava.concert.infrastructure.repository;

import io.hhplus.tdd.hhplusconcertjava.concert.infrastructure.entity.Reservation2Entity;
import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

public interface IReservation2JpaRepository extends JpaRepository<Reservation2Entity, Long> {

    @Query(value = """
        select R
        from Reservation2Entity R 
        where R.concert_seat_id= :concertSeatId
            and R.concert_time_id = :concertTimeId
    """)
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value = "10000")})
    public Reservation2Entity duplicateCheck(@Param("concertSeatId") Long concertSeatId, @Param("concertTimeId") Long concertTimeId);


    @Query(value = """
        delete from reservation2
    """,
    nativeQuery = true)
    @Modifying
    public void clearTable();
}
