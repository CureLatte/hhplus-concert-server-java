package io.hhplus.tdd.hhplusconcertjava.common.error;

public record ErrorResponseDto(
        String status,
        String message
) {
}
