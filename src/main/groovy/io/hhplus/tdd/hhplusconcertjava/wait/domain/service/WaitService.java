package io.hhplus.tdd.hhplusconcertjava.wait.domain.service;

import io.hhplus.tdd.hhplusconcertjava.wait.domain.entity.WaitQueue;
import io.hhplus.tdd.hhplusconcertjava.wait.domain.repository.WaitQueueRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class WaitService implements IWaitService{

    WaitQueueRepository waitQueueRepository;

    @Override
    public WaitQueue getWaitQueue(String uuid){

        WaitQueue waitQueue = this.waitQueueRepository.findByUUID(uuid);

        if(waitQueue == null){
            // 새로 생성
            waitQueue = this.waitQueueRepository.create();

        }


        return waitQueue;
    }

    @Override
    public WaitQueue updateWaitQueue(WaitQueue waitQueue) {
        return this.waitQueueRepository.save(waitQueue);
    }


}
