package io.hhplus.tdd.hhplusconcertjava.wait.domain.repository;

import io.hhplus.tdd.hhplusconcertjava.user.domain.entity.User;
import io.hhplus.tdd.hhplusconcertjava.wait.domain.entity.WaitToken;

import java.util.List;

public interface WaitTokenRepository {

    public WaitToken getWaitToken(String uuid);

    public WaitToken createWaitToken();

    public List<WaitToken> getFastestWaitTokens(Long tokenCnt);

    public void deleteToken(WaitToken waitToken);
}
