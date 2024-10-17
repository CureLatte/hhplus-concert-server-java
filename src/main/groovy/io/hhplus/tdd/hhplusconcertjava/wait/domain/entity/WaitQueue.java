package io.hhplus.tdd.hhplusconcertjava.wait.domain.entity;

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

   public enum WaitStatus{
       WAIT, PROCESS, FINISH
   }


}
