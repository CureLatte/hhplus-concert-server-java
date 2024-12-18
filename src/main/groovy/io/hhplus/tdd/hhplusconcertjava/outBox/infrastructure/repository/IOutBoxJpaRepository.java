package io.hhplus.tdd.hhplusconcertjava.outBox.infrastructure.repository;

import io.hhplus.tdd.hhplusconcertjava.outBox.domain.domain.OutBox;
import io.hhplus.tdd.hhplusconcertjava.outBox.infrastructure.entity.OutBoxEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IOutBoxJpaRepository extends JpaRepository<OutBoxEntity, Long> {
    @Query(value= """
        select O from OutBoxEntity O 
        where O.topic = :topic
        and O.eventKey = :eventKey
        and O.payload = :payload
        
""")
    public List<OutBoxEntity> findByTopicKeyPayload(@Param("topic") String topic, @Param("eventKey") String eventKey, @Param("payload") String payload);

    @Query(value ="""
        select * from out_box as O 
        where O.status = :status
        and TIMESTAMPDIFF(minute, O.created_at, now()) >= 5
    """, nativeQuery = true)
    public List<OutBoxEntity> findByStatus(@Param("status") String status);
}
