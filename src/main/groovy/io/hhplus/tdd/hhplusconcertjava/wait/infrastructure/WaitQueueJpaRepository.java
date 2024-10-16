package io.hhplus.tdd.hhplusconcertjava.wait.infrastructure;

import io.hhplus.tdd.hhplusconcertjava.wait.domain.entity.WaitQueue;
import io.hhplus.tdd.hhplusconcertjava.wait.domain.repository.WaitQueueRepository;
import io.hhplus.tdd.hhplusconcertjava.wait.infrastructure.entity.WaitQueueEntity;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@AllArgsConstructor
public class WaitQueueJpaRepository implements WaitQueueRepository {
    private IWaitQueueJpaRepository iwaitQueueJpaRepository;

    @Override
    public WaitQueue findByUUID(String uuid) {

        if(uuid == null){
            return null;
        }

        List<WaitQueueEntity> entityList = this.iwaitQueueJpaRepository.findByUUID(uuid);

        if(entityList.size() == 0) {
            return null;
        }

        return entityList.get(0).toDomain();
    }

    @Override
    public WaitQueue create() {

        WaitQueueEntity entity = new WaitQueueEntity();

        UUID uuid = UUID.randomUUID();
        entity.setUuid(uuid.toString());

        entity.setStatus(WaitQueue.WaitStatus.WAIT.name());

        this.iwaitQueueJpaRepository.save(entity);

        return entity.toDomain();
    }

    @Override
    public WaitQueue save(WaitQueue waitQueue) {
        WaitQueueEntity entity = WaitQueueEntity.fromDomain(waitQueue);

        this.iwaitQueueJpaRepository.save(entity);

        return entity.toDomain();
    }


}