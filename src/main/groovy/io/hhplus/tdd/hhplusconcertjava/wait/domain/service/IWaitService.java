package io.hhplus.tdd.hhplusconcertjava.wait.domain.service;

import io.hhplus.tdd.hhplusconcertjava.wait.domain.entity.WaitQueue;

public interface IWaitService {

    public WaitQueue getWaitQueue(String uuid);

    public WaitQueue updateWaitQueue(WaitQueue waitQueue);
}
