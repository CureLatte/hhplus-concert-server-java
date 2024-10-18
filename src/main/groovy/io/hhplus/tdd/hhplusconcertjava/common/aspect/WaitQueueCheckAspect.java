package io.hhplus.tdd.hhplusconcertjava.common.aspect;

import io.hhplus.tdd.hhplusconcertjava.concert.interfaces.dto.PostReserveSeatRequestDto;
import io.hhplus.tdd.hhplusconcertjava.wait.domain.service.WaitService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Aspect
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class WaitQueueCheckAspect {

    private final HttpServletRequest httpServletRequest;


    WaitService waitService;

    @Pointcut("@annotation(io.hhplus.tdd.hhplusconcertjava.common.annotaion.WaitQueueCheck)")
    public void pointcut() {
    }

    @Before("pointcut()")
    public void tokenCheck(JoinPoint joinPoint) {
        //인증 로직 구현하기.
        //ex. header의 Authorization 값 체크 로직
        String token = httpServletRequest.getHeader("token");



        this.waitService.checkWaitQueue(token);

        Object[] args = joinPoint.getArgs();
        if(args[0].getClass() == PostReserveSeatRequestDto.class){
            PostReserveSeatRequestDto.uuid = token;
        }



    }
}
