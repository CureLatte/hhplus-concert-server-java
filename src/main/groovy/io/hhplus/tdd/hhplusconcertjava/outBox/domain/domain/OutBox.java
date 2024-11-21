package io.hhplus.tdd.hhplusconcertjava.outBox.domain.domain;

import io.hhplus.tdd.hhplusconcertjava.common.error.BusinessError;
import io.hhplus.tdd.hhplusconcertjava.common.error.ErrorCode;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class OutBox {
    public Long id;
    public String topic;
    public String payload;
    public OutBoxStatus status;
    public String eventKey;
    public LocalDateTime createdAt;
    public LocalDateTime updatedAt;
    public LocalDateTime deletedAt;

    public enum OutBoxStatus{
        INIT, RECEIVE, SUCCESS
    }

    public void init(){
        this.status = OutBoxStatus.INIT;
    }


    public void receive(){
        if(this.status != OutBoxStatus.INIT){
            throw new BusinessError(ErrorCode.ALREADY_RECEIVE_OUT_BOX_ERROR.getStatus(), ErrorCode.ALREADY_RECEIVE_OUT_BOX_ERROR.getMessage());
        }

        this.status = OutBoxStatus.RECEIVE;
    }

    public void success(){
        this.status = OutBoxStatus.SUCCESS;
    }

}
