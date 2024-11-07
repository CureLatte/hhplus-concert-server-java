package io.hhplus.tdd.hhplusconcertjava.wait.domain.repository;

import io.hhplus.tdd.hhplusconcertjava.wait.domain.entity.ActivateToken;

public interface ActivateTokenRepository {

    public ActivateToken create(String uuid);
}
