package io.hhplus.tdd.hhplusconcertjava.integration.point;


import io.hhplus.tdd.hhplusconcertjava.integration.TestBaseIntegration;
import io.hhplus.tdd.hhplusconcertjava.point.domain.entity.Point;
import io.hhplus.tdd.hhplusconcertjava.point.domain.entity.PointHistory;
import io.hhplus.tdd.hhplusconcertjava.point.domain.repository.PointHistoryRepository;
import io.hhplus.tdd.hhplusconcertjava.point.domain.repository.PointRepository;
import io.hhplus.tdd.hhplusconcertjava.point.domain.service.PointService;
import io.hhplus.tdd.hhplusconcertjava.user.domain.entity.User;
import io.hhplus.tdd.hhplusconcertjava.user.domain.repository.UserRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TestPointService {

    @Nested
    public class TestUseCancel extends TestBaseIntegration{
        @Autowired
        PointService pointService;

        @Autowired
        UserRepository userRepository;

        @Autowired
        PointRepository pointRepository;
        @Autowired
        PointHistoryRepository pointHistoryRepository;

        @Test
        public void 보상_트랜잭션_포인트_복구_확인(){
            // GIVEN
            User user = this.userRepository.create(User.builder()
                            .name("test1")
                    .build());

            Point point = this.pointService.getPoint(user);
            // 포인트 생성
            this.pointService.charge(point, 1000);

            // WHEN
            PointHistory pointHistory = this.pointService.use(point, 500);
            this.pointService.useCancel(pointHistory);

            // THEN
            Point updatedPoint = this.pointRepository.findByUser(user);
            assertEquals(1000, updatedPoint.getBalance());

            List<PointHistory> pointHistoryList = this.pointHistoryRepository.findByStatus(PointHistory.PointStatus.USE);
            assertNull(pointHistoryList);
        }
    }
}
