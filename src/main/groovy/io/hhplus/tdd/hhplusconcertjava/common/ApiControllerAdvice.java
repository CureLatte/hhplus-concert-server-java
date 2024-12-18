package io.hhplus.tdd.hhplusconcertjava.common;

import io.hhplus.tdd.hhplusconcertjava.common.error.BusinessError;
import io.hhplus.tdd.hhplusconcertjava.common.error.ErrorResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
class ApiControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorResponseDto> handleExceptionError(Exception error) {

        System.out.println("Business Error:: "  + error);

        return ResponseEntity.status(500).body(new ErrorResponseDto("500", error.getMessage()));
    }


    @ExceptionHandler(value = BusinessError.class)
    public ResponseEntity<ErrorResponseDto> handleBusinessError(BusinessError businessError) {

        System.out.println("Business Error:: "  + businessError);

        return ResponseEntity.status(businessError.status).body(new ErrorResponseDto(businessError.status.toString(), businessError.getMessage()));
    }
}
