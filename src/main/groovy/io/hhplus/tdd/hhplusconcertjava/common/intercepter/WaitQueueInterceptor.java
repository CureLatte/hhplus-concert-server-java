package io.hhplus.tdd.hhplusconcertjava.common.intercepter;

import io.hhplus.tdd.hhplusconcertjava.common.error.BusinessError;
import io.hhplus.tdd.hhplusconcertjava.wait.domain.service.WaitService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class WaitQueueInterceptor implements HandlerInterceptor {

    WaitService waitService;

    @Override
    public boolean preHandle(
            HttpServletRequest request, HttpServletResponse response,
            Object obj) throws Exception {


        String token = request.getHeader("token");
        log.info("token {}", token);

        this.waitService.checkActivateToken(token);

        return true;

    }

}
