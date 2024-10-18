package io.hhplus.tdd.hhplusconcertjava.point.apllication;

import io.hhplus.tdd.hhplusconcertjava.point.domain.entity.Point;
import io.hhplus.tdd.hhplusconcertjava.point.domain.service.PointService;
import io.hhplus.tdd.hhplusconcertjava.point.domain.service.PointServiceImpl;
import io.hhplus.tdd.hhplusconcertjava.point.interfaces.dto.GetPointResponseDto;
import io.hhplus.tdd.hhplusconcertjava.user.domain.entity.User;
import io.hhplus.tdd.hhplusconcertjava.user.domain.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PointFacade {

    UserService userService;
    PointService pointService;


    public GetPointResponseDto getPoint(Long userId){

        User user = this.userService.getUser(userId);


        Point point = this.pointService.getPoint(user);

        return new GetPointResponseDto(point.balance);
    }

}
