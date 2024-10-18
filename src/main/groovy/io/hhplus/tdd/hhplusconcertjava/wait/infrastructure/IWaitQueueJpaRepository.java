package io.hhplus.tdd.hhplusconcertjava.wait.infrastructure;

import io.hhplus.tdd.hhplusconcertjava.wait.infrastructure.entity.WaitQueueEntity;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Hidden
public interface IWaitQueueJpaRepository extends JpaRepository<WaitQueueEntity, Long> {

    @Query(value ="""
            select *
            from wait_queue
            where uuid= :uuidValue
            """,
            nativeQuery = true)
    public List<WaitQueueEntity> findByUUID(@Param("uuidValue") String uuid);


    @Query(value = """  
        delete 
        from wait_queue
        where status = :statusValue
        
    """,
    nativeQuery = true)
    @Modifying
    public void deleteByStatus(@Param("statusValue") String statusValue);

    @Query(value = """
        delete 
        from wait_queue
        where timestampdiff(MINUTE, created_at, now()) >= :targetTime 
           or timestampdiff(MINUTE, created_at, now()) <=-10
            
    """,
    nativeQuery = true)
    @Modifying
    public void deleteByCreateTime(@Param("targetTime") Integer targetTime);

    @Query(value = """
        select count(*) as cnt 
        from wait_queue 
        where status= :statusValue
    
    """,
    nativeQuery = true)
    public Integer countAllByStatus(@Param("statusValue") String statusValue);

    @Query(value = """
        update wait_queue
        inner join (
            select id 
            from wait_queue 
            where status ='WAIT' 
            order by created_at 
            limit :leftCnt
        ) as targetWait on wait_queue.id=targetWait.id
        
        set status = 'PROCESS'
        where status = 'WAIT'
       
    """,
    nativeQuery = true)
    @Modifying(clearAutomatically = true)
    public void updateStatusByCreatedAt(@Param("leftCnt") Integer leftCnt);

}
