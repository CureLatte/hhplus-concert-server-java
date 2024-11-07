package io.hhplus.tdd.hhplusconcertjava.integration.wait;

import io.hhplus.tdd.hhplusconcertjava.integration.TestBaseIntegration;
import io.hhplus.tdd.hhplusconcertjava.wait.domain.entity.ActivateToken;
import io.hhplus.tdd.hhplusconcertjava.wait.domain.entity.WaitToken;
import io.hhplus.tdd.hhplusconcertjava.wait.domain.repository.ActivateTokenRepository;
import io.hhplus.tdd.hhplusconcertjava.wait.domain.repository.WaitTokenRepository;
import io.hhplus.tdd.hhplusconcertjava.wait.domain.service.WaitService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;

import java.security.Key;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class TestWaitService {

    @Nested
    class TestGetWaitToken extends TestBaseIntegration{

        @Autowired
        WaitService waitService;

        @Autowired
        RedisTemplate<String, String> redisTemplate;



        @BeforeEach
        public void setting(){
            ZSetOperations<String, String> zSetOperations = redisTemplate.opsForZSet();
            zSetOperations.removeRange("WaitQueue", 0, -1);
        }

        @Test
        public void 토큰_생성_테스트__성공(){
            // GIVEN

            // WHEN
             WaitToken waitToken = waitService.getWaitToken(null);


            // THEN
             assertEquals(0, waitToken.getRank());
        }

        @Test
        public void 토큰_생성_순서_확인__성공(){
            // GIVEN
            WaitToken firstToken = waitService.getWaitToken(null);
            WaitToken secondToken = waitService.getWaitToken(null);

            // WHEN

            WaitToken getFirstToken = waitService.getWaitToken(firstToken.getUuid());

            WaitToken getSecondToken = waitService.getWaitToken(secondToken.getUuid());

            // THEN
            assertEquals(0, getFirstToken.getRank());
            assertEquals(1, getSecondToken.getRank());

        }

    }

    @Nested
    class TestUpdateToken extends TestBaseIntegration{

        @Autowired
        WaitService waitService;

        @Autowired
        WaitTokenRepository waitTokenRepository;

        @Autowired
        ActivateTokenRepository activateTokenRepository;


        @Test
        public void 새로운_토큰_기존_토큰_삭제__성공(){
            // GIVEN
            Long updateCnt = 10L;
            WaitToken waitToken = this.waitService.getWaitToken(null);


            // WHEN
            this.waitService.updateWaitToken(updateCnt);


            // THEN
            WaitToken checkUpdateToken = this.waitTokenRepository.getWaitToken(waitToken.getUuid());

            assertNull(checkUpdateToken);
        }

        @Test
        public void 새로운_토큰_생성_확인__성공(){
            // GIVEN
            Long updateCnt = 10L;
            WaitToken waitToken = this.waitService.getWaitToken(null);


            // WHEN
            this.waitService.updateWaitToken(updateCnt);



            // THEN
            ActivateToken activateToken = this.activateTokenRepository.get(waitToken.getUuid());

            assertNotNull(activateToken);


        }

        @Test
        public void 새로운_토큰_생성시_TTL_확인__성공(){
            // GIVEN
            Long updateCnt = 10L;
            WaitToken waitToken = this.waitService.getWaitToken(null);


            // WHEN
            try {
                this.waitService.updateWaitToken(updateCnt);

                Thread.sleep(10000);
            } catch (InterruptedException error){

            }

            LocalDateTime expiredAt = LocalDateTime.now().plusSeconds(1800 - 10);

            // THEN
            ActivateToken activateToken = this.activateTokenRepository.get(waitToken.getUuid());

            assertNotNull(activateToken);

            assertEquals(expiredAt.getMinute(), activateToken.getExpiresAt().getMinute());
        }

    }
}
