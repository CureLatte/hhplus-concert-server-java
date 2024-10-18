package io.hhplus.tdd.hhplusconcertjava.common;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusinessError extends RuntimeException {
    public String message;
    public Integer status = 500;

    public BusinessError(String message) {
        this.message = message;
    }

    public BusinessError(int status, String message) {
        this.status = Integer.valueOf(status);
        this.message = message;

    }
}
