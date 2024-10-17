package io.hhplus.tdd.hhplusconcertjava.common.aspect;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
@RequiredArgsConstructor
public class TestAspect {

    private final HttpServletRequest httpServletRequest;
    private static final String AUTHORIZATION = "Authorization";

    @Pointcut("@annotation(io.hhplus.tdd.hhplusconcertjava.common.annotaion.CustomCheck)")
    public void pointcut() {
    }

    @Before("pointcut()")
    public void Authorization(JoinPoint joinPoint) {
        //인증 로직 구현하기.
        //ex. header의 Authorization 값 체크 로직
        String token = httpServletRequest.getHeader(AUTHORIZATION);

        System.out.println(token);

        System.out.println("joinPiont !!! ---->" + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());


    }

}
