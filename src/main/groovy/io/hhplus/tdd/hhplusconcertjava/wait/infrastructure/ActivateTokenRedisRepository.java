package io.hhplus.tdd.hhplusconcertjava.wait.infrastructure;

import io.hhplus.tdd.hhplusconcertjava.wait.domain.entity.ActivateToken;
import io.hhplus.tdd.hhplusconcertjava.wait.domain.repository.ActivateTokenRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.time.LocalDateTime;

@Slf4j
@Repository
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ActivateTokenRedisRepository implements ActivateTokenRepository {
    RedisTemplate<String, String> redisTemplate;

    public final Duration TTL = Duration.ofMinutes(30);


    @Override
    public ActivateToken get(String uuid) {

        ValueOperations<String, String> operations = redisTemplate.opsForValue();

        String value = operations.get(uuid);

        if(value == null) {
            return null;
        }

        Long leftTime = redisTemplate.getExpire(uuid);

        log.info("leftTime:  {}", leftTime);

        return ActivateToken.builder()
                .uuid(uuid)
                .expiresAt(LocalDateTime.now().plusSeconds(leftTime))
                .build();
    }

    @Override
    public ActivateToken create(String uuid) {

        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        // 30 분 TTL
        operations.set( uuid, "active" );

        redisTemplate.expire(uuid, TTL);


        Long leftTimeSeconds = redisTemplate.getExpire(uuid);

        log.info("leftTimeSeconds: {}", leftTimeSeconds);

        ActivateToken token = ActivateToken.builder()
                .uuid(uuid)
                .build();

        return token;
    }
}
