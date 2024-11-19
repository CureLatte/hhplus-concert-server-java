package io.hhplus.tdd.hhplusconcertjava.point.infrastructure;

import io.hhplus.tdd.hhplusconcertjava.point.domain.entity.UseCancelEvent;

public interface PointEventListener {

    public void useCancel(UseCancelEvent useCancelEvent);
}
