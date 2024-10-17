package io.hhplus.tdd.hhplusconcertjava.wait.domain.service;

import io.hhplus.tdd.hhplusconcertjava.wait.domain.entity.WaitQueue;
import io.hhplus.tdd.hhplusconcertjava.wait.domain.repository.WaitQueueRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    @Transactional
    public void updateProcessWaitQueue() {


        // finish 인 경우 -> delete
        this.waitQueueRepository.deleteFinish();


        // 작업 시간 생성 후 30분 이상 차이 -> delete
        this.waitQueueRepository.deleteTimeout();


        // 남은 작업 인원 확인
        Integer leftCnt = this.waitQueueRepository.countProcess();
        WaitQueue waitQueue = WaitQueue.builder().build();

        // 30명 이상인경우 return
        if(leftCnt >= waitQueue.MaxCnt){
            return;
        }

        // wait 변경 인원수 파악
        Integer addCnt = waitQueue.getMaxCnt() - leftCnt;


        // update
        this.waitQueueRepository.updateStatusOrderByCreatedAt(addCnt);


    }


}
