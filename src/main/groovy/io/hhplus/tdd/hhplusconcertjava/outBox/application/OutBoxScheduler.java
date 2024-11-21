package io.hhplus.tdd.hhplusconcertjava.outBox.application;

import io.hhplus.tdd.hhplusconcertjava.outBox.domain.service.OutBoxService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class OutBoxScheduler {

    OutBoxService outBoxService;


    @Scheduled(cron = "0 */5 * * * *")
    public void rePublish(){
        outBoxService.rePublish();
    }
}
