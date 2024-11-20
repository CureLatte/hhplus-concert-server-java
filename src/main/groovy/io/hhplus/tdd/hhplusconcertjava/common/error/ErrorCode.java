package io.hhplus.tdd.hhplusconcertjava.common.error;

import lombok.Getter;

@Getter
public enum ErrorCode {
    // concert
    CONCERT_TIME_SOLE_OUT_ERROR(400, "CONCERT_001", "예약이 완료된 날짜입니다" ),
    ALREADY_RESERVATION_ERROR(400, "CONCERT_002", "이미 예약된 좌석입니다"),
    LEFT_CNT_DOES_NOT_EXIST_ERROR(400, "CONCERT_003", "잔여 좌석이 존재하지 않습니다."),


    NOT_FOUND_CONCERT_ERROR(400, "CONCERT_004", "존재하지 않은 콘서트 입니다."),
    NOT_FOUND_CONCERT_TIME_ERROR(400, "CONCERT_005", "존재하지 않은 콘서트 날짜 입니다."),
    NOT_FOUND_CONCERT_SEAT_ERROR(400, "CONCERT_006", "존재하지 않은 콘서트 좌석 입니다."),
    NOT_FOUND_RESERVATION_ERROR(400, "CONCERT_007", "존재하지 않은 예약 입니다."),
    DUPLICATION_RESERVATION_ERROR(400, "CONCERT_008", "이미 신청한 예약입니다."),

    // wait
    CHECK_PROCESS_ERROR(400, "WAIT_001", "접근 권한이 없습니다."),
    NOT_FOUND_TOKEN_ERROR(400, "WAIT_002", "토큰이 존재하지 않습니다."),

    // user
    NOT_FOUND_USER_ERROR(400, "USER_001", "존재하지 않은 유저 입니다."),

    // payment
    WRONG_PAY_AMOUNT_ERROR(400, "payment_001", "결제금액이 맞지 않습니다."),
    ALREADY_PAY_ERROR(400, "payment_002", "이미 결제 완료된 예약 입니다.")


    ;
    private final int status;
    private final String code;
    private final String message;

    ErrorCode(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

}


