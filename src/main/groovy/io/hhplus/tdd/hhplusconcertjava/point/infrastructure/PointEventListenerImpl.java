package io.hhplus.tdd.hhplusconcertjava.point.infrastructure;

import io.hhplus.tdd.hhplusconcertjava.point.domain.entity.UseCancelEvent;
import io.hhplus.tdd.hhplusconcertjava.point.domain.service.PointService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor(onConstructor_ = @Autowired)
public class PointEventListenerImpl implements PointEventListener {
    PointService pointService;

    @EventListener
    public void useCancel(UseCancelEvent useCancelEvent) {
        log.info("[EVENT] UseCancelEvent: {}", useCancelEvent);
        this.pointService.useCancel(useCancelEvent.getPointHistory());
    }

}
