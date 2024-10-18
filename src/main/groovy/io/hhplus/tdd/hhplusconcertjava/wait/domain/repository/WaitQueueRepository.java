package io.hhplus.tdd.hhplusconcertjava.wait.domain.repository;

import io.hhplus.tdd.hhplusconcertjava.wait.domain.entity.WaitQueue;
import org.springframework.stereotype.Repository;

@Repository
public interface WaitQueueRepository {

    public WaitQueue findByUUID(String uuid);

    public WaitQueue create();

    public WaitQueue save(WaitQueue waitQueue);

    public void deleteFinish();

    public void deleteTimeout();

    public Integer countProcess();

    public void updateStatusOrderByCreatedAt(Integer leftCnt);
}
