package io.hhplus.tdd.hhplusconcertjava.common;

public class BusinessError extends RuntimeException {
    public String message;
    public String status = "500";

    public BusinessError(String message) {
        this.message = message;
    }

    public BusinessError(String status, String message) {
        this.status = status;
        this.message = message;

    }
}
