package io.hhplus.tdd.hhplusconcertjava.wait.application;

import io.hhplus.tdd.hhplusconcertjava.wait.domain.entity.WaitQueue;
import io.hhplus.tdd.hhplusconcertjava.wait.domain.service.WaitService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class WaitFacade {

    WaitService waitService;


    public WaitQueue getWaitToken(String uuid, String userId){

        WaitQueue waitQueue = this.waitService.getWaitQueue(uuid);

        System.out.println(waitQueue);


        if(userId != null) {
            waitQueue.setUserId(Long.getLong(userId));
            this.waitService.updateWaitQueue(waitQueue);
        }

        return waitQueue;
    }
}
