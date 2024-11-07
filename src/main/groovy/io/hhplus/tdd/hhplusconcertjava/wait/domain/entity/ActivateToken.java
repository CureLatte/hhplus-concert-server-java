package io.hhplus.tdd.hhplusconcertjava.wait.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@ToString
public class ActivateToken {
    String uuid;
    LocalDateTime expiresAt;
}
