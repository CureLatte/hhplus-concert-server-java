package io.hhplus.tdd.hhplusconcertjava.wait.domain.entity;

import io.hhplus.tdd.hhplusconcertjava.common.error.BusinessError;
import io.hhplus.tdd.hhplusconcertjava.common.error.ErrorCode;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
public class WaitQueue {
    long id;
    String uuid;
    long userId;
    WaitStatus status;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    public final int MaxCnt=30;
    public final int MaxMinute=30;

   public enum WaitStatus{
       WAIT, PROCESS, FINISH
   }

    public void validate() throws BusinessError {
        // check
        if(!this.status.equals(WaitStatus.PROCESS)){
            throw new BusinessError(ErrorCode.CHECK_PROCESS_ERROR.getStatus(), ErrorCode.CHECK_PROCESS_ERROR.getMessage());
        }

    }
}
