package io.hhplus.tdd.hhplusconcertjava.concert.domain.entity;

import io.hhplus.tdd.hhplusconcertjava.common.error.BusinessError;
import io.hhplus.tdd.hhplusconcertjava.common.error.ErrorCode;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder
@ToString
public class ConcertTime {
    public Long id;
    public Concert concert;
    public LocalDateTime startTime;
    public LocalDateTime endTime;
    public ConcertTimeStatus status;
    public int maxCnt;
    public int leftCnt;
    public int price;


    public enum ConcertTimeStatus{
        ON_SALE, SOLD_OUT
    }

    public void decreaseLeftCnt(){

        if(this.leftCnt <=0){
            throw new BusinessError(ErrorCode.LEFT_CNT_DOES_NOT_EXIST_ERROR.getStatus(), ErrorCode.LEFT_CNT_DOES_NOT_EXIST_ERROR.getMessage());
        }

        this.leftCnt = this.leftCnt - 1;
    }

}
