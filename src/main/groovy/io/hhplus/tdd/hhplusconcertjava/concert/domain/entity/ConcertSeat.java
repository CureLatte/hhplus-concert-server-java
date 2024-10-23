package io.hhplus.tdd.hhplusconcertjava.concert.domain.entity;

import io.hhplus.tdd.hhplusconcertjava.common.BusinessError;
import lombok.*;
import org.springframework.stereotype.Repository;

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

    public final String CONCERT_TIME_SOLE_OUT_ERROR_MESSAGE="예약이 완료된 날짜입니다.";
    public final String ALREADY_RESERVATION_ERROR_MESSAGE="이미 예약된 좌석입니다";

    public enum ConcertSeatStatus{
        EMPTY, PROCESS, RESERVATION, PAY
    }


    public void reservation(String uuid){
        if(this.concertTime.status == ConcertTime.ConcertTimeStatus.SOLD_OUT){
            throw new BusinessError(400, this.CONCERT_TIME_SOLE_OUT_ERROR_MESSAGE);
        }


        if(this.status.equals(ConcertSeatStatus.RESERVATION)){
            throw new BusinessError(400, this.ALREADY_RESERVATION_ERROR_MESSAGE);
        }

        this.status = ConcertSeatStatus.RESERVATION;
        this.uuid = uuid;

    }

}
