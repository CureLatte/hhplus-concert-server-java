package io.hhplus.tdd.hhplusconcertjava.wait.domain.entity;

import io.hhplus.tdd.hhplusconcertjava.common.BusinessError;
import lombok.*;

import java.time.LocalDate;
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

    public final String CHECK_PROCESS_ERROR_MESSAGE="접근 권한이 없습니다.";

   public enum WaitStatus{
       WAIT, PROCESS, FINISH
   }

    public void validate() throws BusinessError {
        // check
        if(!this.status.equals(WaitStatus.PROCESS)){
            throw new BusinessError(400, this.CHECK_PROCESS_ERROR_MESSAGE);
        }

    }
}
