package io.hhplus.tdd.hhplusconcertjava.wait.interfaces;

import io.hhplus.tdd.hhplusconcertjava.common.annotaion.CustomCheck;
import io.hhplus.tdd.hhplusconcertjava.wait.application.WaitFacade;
import io.hhplus.tdd.hhplusconcertjava.wait.domain.entity.WaitQueue;
import io.hhplus.tdd.hhplusconcertjava.wait.interfaces.dto.GetTokenResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@AllArgsConstructor
public class WaitController implements IWaitController{

    WaitFacade waitFacade;

    @Override
    @CustomCheck
    @GetMapping("/wait")
    public GetTokenResponseDto getWaitToken(@RequestHeader("Authorization") String uuid, @RequestParam Map<String, String> params) {

        String userId = params.get("userId");

        WaitQueue waitQueue = this.waitFacade.getWaitToken(uuid,userId);

        return new GetTokenResponseDto(waitQueue.getUuid(), waitQueue.getStatus().name());
    }
}
