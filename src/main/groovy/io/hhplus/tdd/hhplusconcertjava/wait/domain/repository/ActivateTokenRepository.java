package io.hhplus.tdd.hhplusconcertjava.wait.domain.repository;

import io.hhplus.tdd.hhplusconcertjava.wait.domain.entity.ActivateToken;

public interface ActivateTokenRepository {

    public ActivateToken get(String uuid);

    public ActivateToken create(String uuid);

    public void delete(String uuid);
}
