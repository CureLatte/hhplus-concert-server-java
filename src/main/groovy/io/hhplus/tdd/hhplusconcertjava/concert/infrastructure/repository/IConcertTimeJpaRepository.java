package io.hhplus.tdd.hhplusconcertjava.concert.infrastructure.repository;

import io.hhplus.tdd.hhplusconcertjava.concert.infrastructure.entity.ConcertTimeEntity;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.LockModeType;
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
public interface IConcertTimeJpaRepository extends JpaRepository<ConcertTimeEntity, Long> {

    @Query(value = """
        select *
        from concert_time
        where status = 'ON_SALE'
            and left_cnt !=0
            and concert_id = :concertId
    """,
    nativeQuery = true)
    public List<ConcertTimeEntity> findAlLByAvailableTime(@Param("concertId") Long concertId);

    @Query(value = """
    delete from concert_time
    """,
    nativeQuery = true)
    @Modifying
    @Transactional
    public void clearTable();


}
