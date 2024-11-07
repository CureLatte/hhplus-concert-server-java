package io.hhplus.tdd.hhplusconcertjava.wait.domain.repository;

import io.hhplus.tdd.hhplusconcertjava.user.domain.entity.User;
import io.hhplus.tdd.hhplusconcertjava.wait.domain.entity.WaitToken;

public interface WaitTokenRepository {

    public WaitToken getWaitToken(String uuid);

    public WaitToken createWaitToken();


}
