package io.hhplus.tdd.hhplusconcertjava.concert.infrastructure.repository;

import io.hhplus.tdd.hhplusconcertjava.concert.domain.entity.Reservation;
import io.hhplus.tdd.hhplusconcertjava.concert.infrastructure.entity.ReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IReservationJpaRepository extends JpaRepository<ReservationEntity, Long> {

    @Query(value= """
            select *
            from reservation
            where user_id= :userId
                and concert_seat_id= :concertSeatId
                and concert_time_id= :concertTimeId
            order by created_at desc
            limit 1;
            """
    , nativeQuery = true)
    public ReservationEntity findByDuplication(@Param("userId") Long userId, @Param("concertTimeId") Long concertTimeId, @Param("concertSeatId") Long concertSeatId);
}
