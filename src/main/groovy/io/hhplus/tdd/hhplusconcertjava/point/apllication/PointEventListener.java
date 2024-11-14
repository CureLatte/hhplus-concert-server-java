package io.hhplus.tdd.hhplusconcertjava.point.apllication;

import io.hhplus.tdd.hhplusconcertjava.point.domain.service.PointService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
@AllArgsConstructor(onConstructor_ = @Autowired)
public class PointEventListener {
    PointService pointService;

    @EventListener
    public void useCancel(UseCancelEvent useCancelEvent) {
        log.info("[EVENT] UseCancelEvent: {}", useCancelEvent);
        this.pointService.useCancel(useCancelEvent.getPointHistory());
    }

}
