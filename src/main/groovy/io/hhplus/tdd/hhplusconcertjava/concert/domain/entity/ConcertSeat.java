package io.hhplus.tdd.hhplusconcertjava.concert.domain.entity;

import io.hhplus.tdd.hhplusconcertjava.common.error.BusinessError;
import io.hhplus.tdd.hhplusconcertjava.common.error.ErrorCode;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@ToString
public class ConcertSeat {
    public Long id;

    public ConcertTime concertTime;
    public String number;
    public ConcertSeatStatus status;
    public String uuid;
    public LocalDateTime createdAt;
    public LocalDateTime updatedAt;

    public enum ConcertSeatStatus{
        EMPTY, PROCESS, RESERVATION, PAY
    }


    public void reservation(String uuid){
        if(this.concertTime.status == ConcertTime.ConcertTimeStatus.SOLD_OUT){
            throw new BusinessError(ErrorCode.CONCERT_TIME_SOLE_OUT_ERROR.getStatus(), ErrorCode.CONCERT_TIME_SOLE_OUT_ERROR.getMessage());
        }


        if(this.status.equals(ConcertSeatStatus.RESERVATION)){
            throw new BusinessError(ErrorCode.ALREADY_RESERVATION_ERROR.getStatus(), ErrorCode.ALREADY_RESERVATION_ERROR.getMessage());
        }

        this.status = ConcertSeatStatus.RESERVATION;
        this.uuid = uuid;

    }

}
