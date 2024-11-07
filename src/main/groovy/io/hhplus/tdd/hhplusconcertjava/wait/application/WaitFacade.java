package io.hhplus.tdd.hhplusconcertjava.wait.application;

import io.hhplus.tdd.hhplusconcertjava.wait.domain.entity.WaitQueue;
import io.hhplus.tdd.hhplusconcertjava.wait.domain.entity.WaitToken;
import io.hhplus.tdd.hhplusconcertjava.wait.domain.service.WaitService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class WaitFacade {

    private final RedisTemplate<String, String> redisTemplate;

    private final RedisTemplate<String, Objects> redisTemplateObjects;

    WaitService waitService;


    public WaitQueue getWaitQueue(String uuid, String userId){
        WaitQueue waitQueue = this.waitService.getWaitQueue(uuid);

        if(userId != null) {
            waitQueue.setUserId(Long.getLong(userId));
            this.waitService.updateWaitQueue(waitQueue);
        }

        return waitQueue;
    }

    public WaitToken getWaitToken(String uuid){
        return this.waitService.getWaitToken(uuid);
    }

}
