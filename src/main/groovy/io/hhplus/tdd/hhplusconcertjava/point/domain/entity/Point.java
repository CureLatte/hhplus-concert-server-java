package io.hhplus.tdd.hhplusconcertjava.point.domain.entity;


import io.hhplus.tdd.hhplusconcertjava.common.error.BusinessError;
import io.hhplus.tdd.hhplusconcertjava.user.domain.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class Point {
    public Long id;
    public User user;
    public Integer balance;

    public final String POINT_AMOUNT_VALIDATE_ERROR_MESSAGE="충전 금액은 0원 이상이 어야 합니다.";
    public final String BALANCE_DOES_NOT_EXIST_ERROR_MESSAGE = "충전 금액이 부족합니다.";

    public void charge(int pointAmount){
        if(pointAmount <=0){
            throw new BusinessError(400, this.POINT_AMOUNT_VALIDATE_ERROR_MESSAGE);
        }

        this.balance += pointAmount;


    }

    public void use(int pointAmount){
        if(this.balance - pointAmount <0){
            throw new BusinessError(400, this.BALANCE_DOES_NOT_EXIST_ERROR_MESSAGE);
        }

        this.balance -= pointAmount;

    }
}
