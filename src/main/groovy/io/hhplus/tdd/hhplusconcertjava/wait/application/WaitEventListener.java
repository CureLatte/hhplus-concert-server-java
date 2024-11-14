package io.hhplus.tdd.hhplusconcertjava.wait.application;

import io.hhplus.tdd.hhplusconcertjava.wait.domain.service.WaitService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class WaitEventListener {
    WaitService waitService;

    // @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @EventListener
    public void deleteActivateToken(DeleteActivateTokenEvent deleteActivateTokenEvent) throws InterruptedException {

        log.info("EVENT ON !! uuid: {}", deleteActivateTokenEvent);
        this.waitService.deleteActivateToken(deleteActivateTokenEvent.getUuid());

    }
}
