package io.hhplus.tdd.hhplusconcertjava.wait.infrastructure;

import io.hhplus.tdd.hhplusconcertjava.wait.domain.entity.DeleteActivateTokenEvent;
import io.hhplus.tdd.hhplusconcertjava.wait.domain.service.WaitService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class WaitEventListenerImpl implements WaitEventListener {
    WaitService waitService;

    // @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @EventListener
    public void deleteActivateToken(DeleteActivateTokenEvent deleteActivateTokenEvent) {

        log.info("EVENT ON !! uuid: {}", deleteActivateTokenEvent);
        this.waitService.deleteActivateToken(deleteActivateTokenEvent.getUuid());

    }
}
