package io.hhplus.tdd.hhplusconcertjava.point.apllication;

import io.hhplus.tdd.hhplusconcertjava.common.error.BusinessError;
import io.hhplus.tdd.hhplusconcertjava.point.domain.entity.Point;
import io.hhplus.tdd.hhplusconcertjava.point.domain.entity.PointHistory;
import io.hhplus.tdd.hhplusconcertjava.point.domain.service.PointService;
import io.hhplus.tdd.hhplusconcertjava.point.domain.service.PointServiceImpl;
import io.hhplus.tdd.hhplusconcertjava.point.interfaces.dto.GetPointResponseDto;
import io.hhplus.tdd.hhplusconcertjava.point.interfaces.dto.PostPointChargeResponseDto;
import io.hhplus.tdd.hhplusconcertjava.user.domain.entity.User;
import io.hhplus.tdd.hhplusconcertjava.user.domain.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PointFacade {

    UserService userService;
    PointService pointService;


    public GetPointResponseDto getPoint(Long userId){

        try {

            User user = this.userService.getUser(userId);


            Point point = this.pointService.getPoint(user);

            return new GetPointResponseDto(point.balance);

        } catch (BusinessError businessError) {

            log.error("[PointFacade] getPoint: {}", businessError.getMessage());
            throw businessError;
        }


    }


    public PostPointChargeResponseDto chargePoint(Long userId, int chargePoint){

        try {

            User user = this.userService.getUser(userId);

            Point point = this.pointService.getPoint(user);

            PointHistory pointHistory = this.pointService.charge(point, chargePoint);

            return new PostPointChargeResponseDto(point.balance);

        } catch (BusinessError businessError) {

            log.error("[PointFacade] chargePoint: {}", businessError.getMessage());

            throw businessError;

        }

    }
}
