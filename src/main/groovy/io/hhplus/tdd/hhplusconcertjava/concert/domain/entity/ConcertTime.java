package io.hhplus.tdd.hhplusconcertjava.concert.domain.entity;

import io.hhplus.tdd.hhplusconcertjava.common.BusinessError;
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

    public final String LEFT_CNT_DOES_NOT_EXIST_ERROR_MESSAGE="잔여 좌석이 존재하지 않습니다.";

    public enum ConcertTimeStatus{
        ON_SALE, SOLD_OUT
    }

    public void decreaseLeftCnt(){

        if(this.leftCnt <=0){
            throw new BusinessError(400, this.LEFT_CNT_DOES_NOT_EXIST_ERROR_MESSAGE);
        }

        this.leftCnt--;
    }

}
