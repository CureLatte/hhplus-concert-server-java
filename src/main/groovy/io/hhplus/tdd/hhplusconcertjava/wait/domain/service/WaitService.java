package io.hhplus.tdd.hhplusconcertjava.wait.domain.service;

import io.hhplus.tdd.hhplusconcertjava.common.error.BusinessError;
import io.hhplus.tdd.hhplusconcertjava.common.error.ErrorCode;
import io.hhplus.tdd.hhplusconcertjava.wait.domain.entity.WaitQueue;
import io.hhplus.tdd.hhplusconcertjava.wait.domain.entity.WaitToken;
import io.hhplus.tdd.hhplusconcertjava.wait.domain.repository.ActivateTokenRepository;
import io.hhplus.tdd.hhplusconcertjava.wait.domain.repository.WaitQueueRepository;
import io.hhplus.tdd.hhplusconcertjava.wait.domain.repository.WaitTokenRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@AllArgsConstructor
public class WaitService implements IWaitService{

    WaitQueueRepository waitQueueRepository;
    WaitTokenRepository waitTokenRepository;
    ActivateTokenRepository activateTokenRepository;


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

    @Override
    public void checkWaitQueue(String uuid) {
        // wait queue Check
        if(uuid == null){
            throw new BusinessError(ErrorCode.NOT_FOUND_TOKEN_ERROR.getStatus(), ErrorCode.NOT_FOUND_TOKEN_ERROR.getMessage());
        }

        WaitQueue waitQueue = this.waitQueueRepository.findByUUID(uuid);

        if(waitQueue == null){
            throw new BusinessError(ErrorCode.NOT_FOUND_CONCERT_ERROR.getStatus(), ErrorCode.NOT_FOUND_TOKEN_ERROR.getMessage());
        }

        waitQueue.validate();

    }

    @Override
    public WaitToken getWaitToken(String uuid) {

        WaitToken waitToken = this.waitTokenRepository.getWaitToken(uuid);

        if(waitToken == null){
            waitToken = this.waitTokenRepository.createWaitToken();
        }

        log.info(waitToken.toString());

        return waitToken;
    }


}
