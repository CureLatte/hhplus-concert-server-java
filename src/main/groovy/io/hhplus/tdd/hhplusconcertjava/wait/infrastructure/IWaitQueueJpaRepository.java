package io.hhplus.tdd.hhplusconcertjava.wait.infrastructure;

import io.hhplus.tdd.hhplusconcertjava.wait.infrastructure.entity.WaitQueueEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IWaitQueueJpaRepository extends JpaRepository<WaitQueueEntity, Long> {

    @Query(value ="""
            select *
            from wait_queue
            where uuid= :uuidValue
            """,
            nativeQuery = true)
    public List<WaitQueueEntity> findByUUID(@Param("uuidValue") String uuid);

}
