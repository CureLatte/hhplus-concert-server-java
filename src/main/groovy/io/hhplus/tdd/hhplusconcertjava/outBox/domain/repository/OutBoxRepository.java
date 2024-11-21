package io.hhplus.tdd.hhplusconcertjava.outBox.domain.repository;

import io.hhplus.tdd.hhplusconcertjava.outBox.domain.domain.OutBox;

public interface OutBoxRepository {
    public OutBox findById(Long id);
    public OutBox save(OutBox outBox);
}
