package io.hhplus.tdd.hhplusconcertjava.wait.domain.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DeleteActivateTokenEvent {
    String uuid;
}
