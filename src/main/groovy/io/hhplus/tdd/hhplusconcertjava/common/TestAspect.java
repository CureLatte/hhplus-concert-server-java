package io.hhplus.tdd.hhplusconcertjava.common;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class TestAspect {

    @Pointcut("@annotation(io.hhplus.tdd.hhplusconcertjava.common.annotaion.CustomCheck)")
    public void pointcut() {
    }

    @Before("pointcut()")
    public void Authorization(JoinPoint joinPoint) {
        //인증 로직 구현하기.
        //ex. header의 Authorization 값 체크 로직

        System.out.println("joinPiont !!! ---->" + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
    }

}
