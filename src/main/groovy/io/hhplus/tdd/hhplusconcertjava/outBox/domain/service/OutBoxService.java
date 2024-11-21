package io.hhplus.tdd.hhplusconcertjava.outBox.domain.service;

import io.hhplus.tdd.hhplusconcertjava.outBox.domain.domain.OutBox;

public interface OutBoxService {
    public OutBox findById(Long id);

    public OutBox findByOutBox(OutBox outBox);

    public OutBox init(OutBox outBox);

    public OutBox receive(OutBox outBox);

    public OutBox success(OutBox outBox);

    public void rePublish();
}
