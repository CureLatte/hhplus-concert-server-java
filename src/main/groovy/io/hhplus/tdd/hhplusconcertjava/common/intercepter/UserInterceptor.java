package io.hhplus.tdd.hhplusconcertjava.common.intercepter;

import io.hhplus.tdd.hhplusconcertjava.common.error.BusinessError;
import io.hhplus.tdd.hhplusconcertjava.common.error.ErrorCode;
import io.hhplus.tdd.hhplusconcertjava.user.domain.entity.User;
import io.hhplus.tdd.hhplusconcertjava.user.domain.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@AllArgsConstructor
public class UserInterceptor implements HandlerInterceptor {

    UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String userIdString = request.getHeader("Authorization");
        if(userIdString == null){
            throw new BusinessError(ErrorCode.NOT_FOUND_TOKEN_ERROR.getStatus(), ErrorCode.NOT_FOUND_TOKEN_ERROR.getMessage());
        }

        Long userId = Long.valueOf(userIdString);

        this.userService.getUser(userId);

        return true;
    }
}
