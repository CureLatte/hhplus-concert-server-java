package io.hhplus.tdd.hhplusconcertjava.wait.domain.service;

import io.hhplus.tdd.hhplusconcertjava.wait.domain.entity.ActivateToken;
import io.hhplus.tdd.hhplusconcertjava.wait.domain.entity.WaitQueue;
import io.hhplus.tdd.hhplusconcertjava.wait.domain.entity.WaitToken;

public interface IWaitService {

    public WaitQueue getWaitQueue(String uuid);

    public WaitQueue updateWaitQueue(WaitQueue waitQueue);

    public void updateProcessWaitQueue();

    public void checkWaitQueue(String uuid);

    public WaitToken getWaitToken(String uuid);

    public void updateWaitToken(Long updateCnt);

    // activateToken
    public ActivateToken getActivateToken(String uuid);

    public void checkActivateToken(String uuid);

    public void deleteActivateToken(String uuid);
}
