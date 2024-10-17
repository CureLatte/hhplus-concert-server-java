package io.hhplus.tdd.hhplusconcertjava.common.aspect;

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
    private static final String AUTHORIZATION = "Authorization";


    WaitService waitService;

    @Pointcut("@annotation(io.hhplus.tdd.hhplusconcertjava.common.annotaion.WaitQueueCheck)")
    public void pointcut() {
    }

    @Before("pointcut()")
    public void Authorization(JoinPoint joinPoint) {
        //인증 로직 구현하기.
        //ex. header의 Authorization 값 체크 로직

        String token = httpServletRequest.getHeader(AUTHORIZATION);

        System.out.println(token);

        this.waitService.checkWaitQueue(token);

    }
}
