package io.hhplus.tdd.hhplusconcertjava.wait.interfaces;

import io.hhplus.tdd.hhplusconcertjava.wait.domain.entity.DeleteActivateTokenEvent;
import io.hhplus.tdd.hhplusconcertjava.wait.domain.service.WaitService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class WaitSpringEventListener {
    WaitService waitService;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void deleteActivateToken(DeleteActivateTokenEvent deleteActivateTokenEvent) {

        log.info("EVENT ON !! uuid: {}", deleteActivateTokenEvent);
        this.waitService.deleteActivateToken(deleteActivateTokenEvent.getUuid());

    }
}
