package io.hhplus.tdd.hhplusconcertjava.wait.infrastructure;

import io.hhplus.tdd.hhplusconcertjava.wait.domain.entity.DeleteActivateTokenEvent;

public interface WaitEventListener {
    public void deleteActivateToken(DeleteActivateTokenEvent deleteActivateTokenEvent);
}
