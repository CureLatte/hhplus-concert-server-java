package io.hhplus.tdd.hhplusconcertjava.wait.interfaces;

import io.hhplus.tdd.hhplusconcertjava.wait.domain.entity.DeleteActivateTokenEvent;

public interface WaitEventListener {
    public void deleteActivateToken(DeleteActivateTokenEvent deleteActivateTokenEvent);
}
