package io.hhplus.tdd.hhplusconcertjava.common.aspect;


import io.hhplus.tdd.hhplusconcertjava.concert.interfaces.dto.PostReserveSeatRequestDto;
import io.hhplus.tdd.hhplusconcertjava.user.domain.entity.User;
import io.hhplus.tdd.hhplusconcertjava.user.domain.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Aspect
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserCheckAspect {

    private final HttpServletRequest httpServletRequest;
    private final UserService userService;

    @Pointcut("@annotation(io.hhplus.tdd.hhplusconcertjava.common.annotaion.UserCheck)")
    public void pointcut() {
    }

    public void authorizationCheck(JoinPoint joinPoint) {
        String userIdString =  httpServletRequest.getHeader("userId");
        if(userIdString == null) {
            return ;
        }

        Long userId = Long.valueOf(userIdString);
        User user = this.userService.getUser(userId);

        Object[] args = joinPoint.getArgs();
        if(args[0].getClass() == PostReserveSeatRequestDto.class){
            PostReserveSeatRequestDto.user = user;
        }


    }
}
