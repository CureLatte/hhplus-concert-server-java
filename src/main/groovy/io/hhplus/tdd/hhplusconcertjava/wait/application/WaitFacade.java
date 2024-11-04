package io.hhplus.tdd.hhplusconcertjava.wait.application;

import io.hhplus.tdd.hhplusconcertjava.wait.domain.entity.WaitQueue;
import io.hhplus.tdd.hhplusconcertjava.wait.domain.service.WaitService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class WaitFacade {

    private final RedisTemplate<String, String> redisTemplate;

    private final RedisTemplate<String, Objects> redisTemplateObjects;

    WaitService waitService;


    public WaitQueue getWaitToken(String uuid, String userId){
        WaitQueue waitQueue = this.waitService.getWaitQueue(uuid);

        if(userId != null) {
            waitQueue.setUserId(Long.getLong(userId));
            this.waitService.updateWaitQueue(waitQueue);
        }

        return waitQueue;
    }
}
