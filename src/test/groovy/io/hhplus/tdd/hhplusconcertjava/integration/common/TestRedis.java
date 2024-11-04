package io.hhplus.tdd.hhplusconcertjava.integration.common;

import io.hhplus.tdd.hhplusconcertjava.integration.TestBaseIntegration;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class TestRedis {

    @Nested
    public class TestBasicRedisTemplateMethod extends TestBaseIntegration {

        @Autowired
        RedisTemplate<String, String> redisStringTemplate;



        @Test
        public void 문자열_캐시_테스트(){
            // GIVEN
            String key = "testKey";

            String value = "testValue";

            ValueOperations<String, String> valueOperations = redisStringTemplate.opsForValue();

            valueOperations.set(key, value);


            // WHEN
            ValueOperations<String, String> getOperation = redisStringTemplate.opsForValue();

            String getValue = getOperation.get(key);

            // THEN
            assertEquals(value, getValue);

        }


        @Test
        public void 리스트_구조_테스트(){
            // GIVEN
            String firstKey = "testListKey";
            String secondKey = "secondKey";

            String firstValue = "firstValue";
            String secondValue = "secondValue";


            ListOperations<String, String> listOperations = redisStringTemplate.opsForList();

            listOperations.leftPush(firstKey, firstValue);
            listOperations.leftPush(firstKey, secondValue);

            listOperations.rightPush(secondKey, firstValue);
            listOperations.rightPush(secondKey, secondValue);

            // WHEN
            ListOperations<String, String> getListOperation = redisStringTemplate.opsForList();

            List<String> getFirstList = getListOperation.range(firstKey, 0, -1);

            List<String> getSecondList = getListOperation.range(secondKey, 0, -1);



            // THEN
            assertEquals(2, getFirstList.size());
            assertEquals(secondValue, getFirstList.get(0));
            assertEquals(firstValue, getSecondList.get(0));
        }


        @Test
        public void 해쉬_구조_테스트(){
            // GIVEN
            String testHashKey = "testHashKey";
            String firstKey = "firstKey";
            String firstValue = "firstValue";
            String secondKey = "secondKey";
            String secondValue = "secondValue";


            HashOperations<String, String, String> hashOperations = redisStringTemplate.opsForHash();

            hashOperations.put(testHashKey, firstKey, firstValue);
            hashOperations.put(testHashKey, secondKey, secondValue);


            // WHEN
            HashOperations<String, String, String> getHashOperation = redisStringTemplate.opsForHash();

            Map<String, String> getHash = getHashOperation.entries(testHashKey);



            // THEN
            assertEquals(firstValue, getHash.get(firstKey));
            assertEquals(secondValue, getHash.get(secondKey));
        }


        @Test
        public void 순서_정렬_집합_테스트(){
            // GIVEN
            String key = "testSortedListKey";

            String firstKey = "firstKey";
            Integer firstScore = 100;

            String secondKey = "secondKey";
            Integer secondScore = 200;

            String thirdKey = "thirdKey";
            Integer thirdScore = 300;


            ZSetOperations<String, String> zSetOperations = redisStringTemplate.opsForZSet();

            zSetOperations.add(key, firstKey, firstScore);
            zSetOperations.add(key, firstKey, firstScore);

            zSetOperations.add(key, thirdKey, thirdScore);
            zSetOperations.add(key, secondKey, secondScore);

            // WHEN
            ZSetOperations<String, String> getZSetOperations = redisStringTemplate.opsForZSet();
            Set<String> getSet = getZSetOperations.range(key, 0, -1);

            List<String> getList = getSet.stream().toList();


            // THEN
            assertEquals(3, getSet.size());
            assertEquals(firstKey, getList.get(0));
            assertEquals(secondKey, getList.get(1));
            assertEquals(thirdKey, getList.get(2));
        }
    }
}
