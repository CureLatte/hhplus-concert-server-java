package io.hhplus.tdd.hhplusconcertjava.payment.domain.entity;

import io.hhplus.tdd.hhplusconcertjava.common.error.BusinessError;
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

    public final String WRONG_PAY_AMOUNT_ERROR_MESSAGE = "결제금액이 맞지 않습니다.";
    public final String ALREADY_PAY_ERROR_MESSAGE ="이미 결제 완료된 예약 입니다.";

    public enum PaymentStatus {
        READY, DONE, FAIL, CANCEL
    }

    public void payReservation(){
        // 결제 완료 확인
        if(this.reservation.status != Reservation.ReservationStatus.RESERVATION){
            throw new BusinessError(400, this.ALREADY_PAY_ERROR_MESSAGE);
        }

        // 가격 확인
        if( this.reservation.concertTime.price != this.pointHistory.pointAmount){
            throw new BusinessError(400, this.WRONG_PAY_AMOUNT_ERROR_MESSAGE);
        }


        // setting
        this.reservation.status = Reservation.ReservationStatus.PAY_DONE;
        this.status = PaymentStatus.DONE;


    }
}
