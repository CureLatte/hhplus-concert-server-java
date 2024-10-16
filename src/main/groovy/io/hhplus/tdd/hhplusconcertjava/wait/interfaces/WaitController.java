package io.hhplus.tdd.hhplusconcertjava.wait.interfaces;

import io.hhplus.tdd.hhplusconcertjava.common.annotaion.CustomCheck;
import io.hhplus.tdd.hhplusconcertjava.wait.domain.entity.WaitQueue;
import io.hhplus.tdd.hhplusconcertjava.wait.domain.service.IWaitService;
import io.hhplus.tdd.hhplusconcertjava.wait.interfaces.dto.GetTokenResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@AllArgsConstructor
public class WaitController implements IWaitController{

    IWaitService waitService;

    @Override
    @CustomCheck
    @GetMapping("/wait")
    public GetTokenResponseDto getWaitToken(@RequestHeader("Authorization") String uuid, @RequestParam Map<String, String> params) {

        String userId = params.get("userId");

        WaitQueue waitQueue = this.waitService.getWaitQueue(uuid);

        System.out.println("waitQueue :" + waitQueue);

        if(userId != null) {
            waitQueue.setUserId(Long.getLong(userId));
            this.waitService.updateWaitQueue(waitQueue);
        }

        return new GetTokenResponseDto(waitQueue.getUuid(), waitQueue.getStatus().name());
    }
}
