package io.hhplus.tdd.hhplusconcertjava.payment.domain.entity;

import io.hhplus.tdd.hhplusconcertjava.common.error.BusinessError;
import io.hhplus.tdd.hhplusconcertjava.common.error.ErrorCode;
import io.hhplus.tdd.hhplusconcertjava.concert.domain.entity.Reservation;
import io.hhplus.tdd.hhplusconcertjava.point.domain.entity.PointHistory;
import io.hhplus.tdd.hhplusconcertjava.user.domain.entity.User;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
public class Payment {
    public Long id;
    public User user;
    public Reservation reservation;
    public PointHistory pointHistory;
    public PaymentStatus status;
    public LocalDateTime createdAt;
    public LocalDateTime updatedAt;


    public enum PaymentStatus {
        READY, DONE, FAIL, CANCEL
    }

    public void payReservation(){
        // 결제 완료 확인
        if(this.reservation.status != Reservation.ReservationStatus.RESERVATION){
            throw new BusinessError(ErrorCode.ALREADY_PAY_ERROR.getStatus(), ErrorCode.ALREADY_PAY_ERROR.getMessage());
        }

        // 가격 확인
        if( this.reservation.concertTime.price != this.pointHistory.pointAmount){
            throw new BusinessError(ErrorCode.WRONG_PAY_AMOUNT_ERROR.getStatus(), ErrorCode.WRONG_PAY_AMOUNT_ERROR.getMessage());
        }


        // setting
        this.reservation.status = Reservation.ReservationStatus.PAY_DONE;
        this.status = PaymentStatus.DONE;


    }
}
