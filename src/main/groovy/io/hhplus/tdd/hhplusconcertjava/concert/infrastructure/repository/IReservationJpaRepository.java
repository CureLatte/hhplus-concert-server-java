package io.hhplus.tdd.hhplusconcertjava.concert.infrastructure.repository;

import io.hhplus.tdd.hhplusconcertjava.concert.domain.entity.Reservation;
import io.hhplus.tdd.hhplusconcertjava.concert.infrastructure.entity.ReservationEntity;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.LockModeType;
import org.hibernate.annotations.OptimisticLock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Hidden
public interface IReservationJpaRepository extends JpaRepository<ReservationEntity, Long> {

    @Query(value= """
            select *
            from reservation r
            where r.concert_seat_id= :concertSeatId
                and r.concert_time_id= :concertTimeId
            order by r.created_at desc
            limit 1 for update
            """,
            nativeQuery = true
   )
    @Transactional
    public List<ReservationEntity> findByDuplication(@Param("concertTimeId") Long concertTimeId, @Param("concertSeatId") Long concertSeatId);

    @Query(value= """
        delete from reservation
        """,
        nativeQuery = true)
    @Transactional
    @Modifying
    public void clearTable();
}
