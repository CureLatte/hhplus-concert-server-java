package io.hhplus.tdd.hhplusconcertjava.outBox.domain.repository;

import io.hhplus.tdd.hhplusconcertjava.outBox.domain.domain.OutBox;

import java.util.List;

public interface OutBoxRepository {
    public OutBox findById(Long id);
    public OutBox findByOutBox(OutBox outBox);
    public OutBox save(OutBox outBox);
    public List<OutBox> findByStatus(OutBox.OutBoxStatus status);
}
