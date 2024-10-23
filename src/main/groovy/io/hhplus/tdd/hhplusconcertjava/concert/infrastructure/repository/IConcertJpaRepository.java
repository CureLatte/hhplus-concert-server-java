package io.hhplus.tdd.hhplusconcertjava.concert.infrastructure.repository;

import io.hhplus.tdd.hhplusconcertjava.concert.infrastructure.entity.ConcertEntity;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Hidden
public interface IConcertJpaRepository extends JpaRepository<ConcertEntity, Long> {

    @Query(nativeQuery = true,
        value ="""
            delete from concert
        """)
    @Modifying
    @Transactional
    public void clearTable();
}
