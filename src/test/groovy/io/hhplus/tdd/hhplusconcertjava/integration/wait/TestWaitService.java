package io.hhplus.tdd.hhplusconcertjava.integration.wait;

import io.hhplus.tdd.hhplusconcertjava.common.error.BusinessError;
import io.hhplus.tdd.hhplusconcertjava.common.error.ErrorCode;
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
import java.util.Random;
import java.util.UUID;

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

    @Nested
    class TestCheckActivateToken extends TestBaseIntegration{
        @Autowired
        WaitService waitService;

        @Autowired
        ActivateTokenRepository activateTokenRepository;

        @Test
        public void UUID_가_없을_경우__에러(){

            // GIVEN
            String uuid = null;

            // WHEN
            BusinessError error = assertThrows(BusinessError.class, () -> waitService.checkActivateToken(uuid));

            // THEN
            assertEquals(ErrorCode.NOT_FOUND_TOKEN_ERROR.getStatus(), error.getStatus());
            assertEquals(ErrorCode.NOT_FOUND_TOKEN_ERROR.getMessage(), error.getMessage());
        }

        @Test
        public void 잘못된_UUID_일_경우__예러(){
            // GIVEN
            String uuid = UUID.randomUUID().toString();

            // WHEN
            BusinessError error = assertThrows(BusinessError.class, () -> waitService.checkActivateToken(uuid));

            // THEN
            assertEquals(ErrorCode.NOT_FOUND_TOKEN_ERROR.getStatus(), error.getStatus());
            assertEquals(ErrorCode.NOT_FOUND_TOKEN_ERROR.getMessage(), error.getMessage());

        }


        @Test
        public void 활성화_된_토큰_일_경우__성공(){
            // GIVEN
            String uuid = UUID.randomUUID().toString();

            ActivateToken activateToken = this.activateTokenRepository.create(uuid);


            // WHEN
            assertDoesNotThrow(() -> waitService.checkActivateToken(uuid));

            // THEN


        }
    }

    @Nested
    class TestDeleteActivateToken extends TestBaseIntegration{
        @Autowired
        WaitService waitService;

        @Autowired
        ActivateTokenRepository activateTokenRepository;

        @Test
        public void 토큰_삭제_확인__성공(){
            // Given
            String uuid = UUID.randomUUID().toString();

            ActivateToken activateToken = this.activateTokenRepository.create(uuid);

            // WHEN
            this.waitService.deleteActivateToken(uuid);


            // THEN
            ActivateToken checkActivateToken = this.activateTokenRepository.get(uuid);
            assertNull(checkActivateToken);
        }

    }
}
