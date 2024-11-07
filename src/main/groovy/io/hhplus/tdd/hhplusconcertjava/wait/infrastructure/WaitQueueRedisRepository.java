package io.hhplus.tdd.hhplusconcertjava.wait.infrastructure;

import io.hhplus.tdd.hhplusconcertjava.common.error.BusinessError;
import io.hhplus.tdd.hhplusconcertjava.user.domain.entity.User;
import io.hhplus.tdd.hhplusconcertjava.wait.domain.entity.WaitToken;
import io.hhplus.tdd.hhplusconcertjava.wait.domain.repository.WaitTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.UUID;

@Repository
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class WaitQueueRedisRepository implements WaitTokenRepository {

    RedisTemplate<String, Object> redisTemplate;

    final String WAIT_QUEUE_KEY="WaitQueue";

    @Override
    public WaitToken getWaitToken(String uuid) {

        if(uuid == null){
            return null;
        }

        ZSetOperations <String, Object> zSetOperations = redisTemplate.opsForZSet();

        Long rank = zSetOperations.rank(WAIT_QUEUE_KEY, uuid);
        if(rank == null){
            return null;
        }

        Double timeStamp = zSetOperations.score(WAIT_QUEUE_KEY, uuid);

        return WaitToken.builder()
                .uuid(uuid)
                .rank(rank)
                .timeStamp(timeStamp)
                .build();
    }

    @Override
    public WaitToken createWaitToken() {
        double now =  (double) System.currentTimeMillis();
        String uuid = UUID.randomUUID().toString();

        ZSetOperations<String, Object> ZSetOperation =  redisTemplate.opsForZSet();

        ZSetOperation.add(this.WAIT_QUEUE_KEY,uuid, now);
        Long rank = ZSetOperation.rank(this.WAIT_QUEUE_KEY, uuid);

        return WaitToken.builder()
                .uuid(uuid)
                .timeStamp( now)
                .rank(rank)
                .build();
    }
}
