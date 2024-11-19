package io.hhplus.tdd.hhplusconcertjava.wait.infrastructure;

import io.hhplus.tdd.hhplusconcertjava.wait.domain.entity.WaitToken;
import io.hhplus.tdd.hhplusconcertjava.wait.domain.repository.WaitTokenRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Repository
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class WaitTokenRedisRepository implements WaitTokenRepository {

    RedisTemplate<String, String> redisTemplate;

    public final String WAIT_QUEUE_KEY = "WaitQueue";


    @Override
    public WaitToken getWaitToken(String uuid) {

        if(uuid == null){
            return null;
        }

        ZSetOperations <String, String> zSetOperations = redisTemplate.opsForZSet();

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

        ZSetOperations<String, String> ZSetOperation =  redisTemplate.opsForZSet();

        ZSetOperation.add(WAIT_QUEUE_KEY, uuid, now);
        Long rank = ZSetOperation.rank(this.WAIT_QUEUE_KEY, uuid);

        return WaitToken.builder()
                .uuid(uuid)
                .timeStamp( now)
                .rank(rank)
                .build();
    }

    @Override
    public List<WaitToken> getFastestWaitTokens(Long tokenCnt) {

        ZSetOperations<String, String> zSetOperation = redisTemplate.opsForZSet();


        Set<String> waitQueueList =  zSetOperation.range(this.WAIT_QUEUE_KEY,0,  tokenCnt);

        log.info("waitQueueList: {}" , waitQueueList);

        if(waitQueueList == null || waitQueueList.isEmpty()){
            return null;
        }



        AtomicLong index = new AtomicLong(0);

        return waitQueueList.stream().map(uuid -> {

            Long currentIndex = index.getAndIncrement();

            return WaitToken.builder().uuid(uuid).rank(currentIndex).build();
        }).toList();
    }

    @Override
    public void deleteToken(WaitToken waitToken) {

        ZSetOperations<String, String> zSetOperation = redisTemplate.opsForZSet();

        zSetOperation.remove(WAIT_QUEUE_KEY, waitToken.getUuid());

    }
}
