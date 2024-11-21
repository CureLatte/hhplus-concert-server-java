package io.hhplus.tdd.hhplusconcertjava.wait.domain.event;

import io.hhplus.tdd.hhplusconcertjava.outBox.domain.domain.OutBox;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DeleteActivateTokenEvent {
    OutBox outBox;
}
