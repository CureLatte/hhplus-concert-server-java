package io.hhplus.tdd.hhplusconcertjava.common;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
class ApiControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorResponseDto> handleBusinessError(BusinessError businessError) {

        System.out.println("Business Error:: "  + businessError);

        return ResponseEntity.status(500).body(new ErrorResponseDto(businessError.status, businessError.getMessage()));
    }
}
