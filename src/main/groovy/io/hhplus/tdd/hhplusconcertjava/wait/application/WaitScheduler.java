package io.hhplus.tdd.hhplusconcertjava.wait.application;

import io.hhplus.tdd.hhplusconcertjava.wait.domain.service.IWaitService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class WaitScheduler {

    private IWaitService waitService;


    @Scheduled(cron="* */30 * * * *")
    public void updateProcessCnt(){
        this.waitService.updateProcessWaitQueue();
    }

    @Scheduled(cron = "* */30 * * * *")
    public void updateWaitToken(){
        Long updateTokenCnt = 30L;
        this.waitService.updateWaitToken(updateTokenCnt);

    }

}
