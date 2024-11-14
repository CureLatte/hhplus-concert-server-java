package io.hhplus.tdd.hhplusconcertjava.integration.point;

import io.hhplus.tdd.hhplusconcertjava.integration.TestBaseIntegration;
import io.hhplus.tdd.hhplusconcertjava.point.apllication.UseCancelEvent;
import io.hhplus.tdd.hhplusconcertjava.point.domain.entity.Point;
import io.hhplus.tdd.hhplusconcertjava.point.domain.entity.PointHistory;
import io.hhplus.tdd.hhplusconcertjava.point.domain.service.PointService;
import io.hhplus.tdd.hhplusconcertjava.user.domain.entity.User;
import io.hhplus.tdd.hhplusconcertjava.user.domain.repository.UserRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestPointEvent {

    @Nested
    public class TestUseCancelEvent extends TestBaseIntegration {
        @Autowired
        ApplicationEventPublisher applicationEventPublisher;

        @Autowired
        UserRepository userRepository;

        @Autowired
        PointService pointService;

        @Test
        public void 이벤트_발행__성공(){
            // GIVEN
            User user = this.userRepository.create(User.builder()
                            .name("test1")
                    .build());


            Point point = this.pointService.getPoint(user);

            this.pointService.charge(point, 1000);
            PointHistory pointHistory = this.pointService.use(point, 500);

            // WHEN
            this.applicationEventPublisher.publishEvent(UseCancelEvent.builder().pointHistory(pointHistory).build());

            // THEN
            Point updatedPoint = this.pointService.getPoint(user);

            assertEquals(1000, updatedPoint.balance);

        }


    }
}
