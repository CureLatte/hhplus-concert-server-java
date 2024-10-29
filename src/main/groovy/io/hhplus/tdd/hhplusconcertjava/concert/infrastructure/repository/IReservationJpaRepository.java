package io.hhplus.tdd.hhplusconcertjava.concert.infrastructure.repository;

import io.hhplus.tdd.hhplusconcertjava.concert.domain.entity.Reservation;
import io.hhplus.tdd.hhplusconcertjava.concert.infrastructure.entity.ReservationEntity;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import org.hibernate.annotations.OptimisticLock;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Hidden
public interface IReservationJpaRepository extends JpaRepository<ReservationEntity, Long> {

    @Query(value = """
            select r
            from ReservationEntity r
            where r.concertSeat.id= :concertSeatId
                and r.concertTime.id= :concertTimeId
            order by r.createdAt desc
            """
    )
    @Transactional
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value = "10000")})
    public default List<ReservationEntity> findByDuplication(@Param("concertTimeId") Long concertTimeId, @Param("concertSeatId") Long concertSeatId) {
        return null;
    }


    @Query(value= """
        delete from reservation
        """,
        nativeQuery = true)
    @Transactional
    @Modifying
    public void clearTable();


}
