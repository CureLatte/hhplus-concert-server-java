package io.hhplus.tdd.hhplusconcertjava.wait.interfaces;

import io.hhplus.tdd.hhplusconcertjava.wait.application.WaitFacade;
import io.hhplus.tdd.hhplusconcertjava.wait.domain.entity.WaitQueue;
import io.hhplus.tdd.hhplusconcertjava.wait.domain.entity.WaitToken;
import io.hhplus.tdd.hhplusconcertjava.wait.interfaces.dto.GetTokenResponseDto;
import io.hhplus.tdd.hhplusconcertjava.wait.interfaces.dto.GetWaitTokenResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@AllArgsConstructor
public class WaitController implements IWaitController{

    WaitFacade waitFacade;

    @Override
    @GetMapping("/wait")
    public GetTokenResponseDto getWaitQueue(@RequestHeader Map<String, String> header, @RequestParam Map<String, String> params) {

        String userId = header.get("Authorization");

        String token = header.get("token");

        WaitQueue waitQueue = this.waitFacade.getWaitQueue(token,userId);



        return new GetTokenResponseDto(waitQueue.getUuid(), waitQueue.getStatus().name());
    }

    @Override
    public GetWaitTokenResponseDto getWaitToken(Map<String, String> header, Map<String, String> Params) {
        String userId = header.get("Authorization");

        String token = header.get("token");

        WaitToken waitToken = this.waitFacade.getWaitToken(token);


        return new GetWaitTokenResponseDto(waitToken.getUuid(), waitToken.getTimeStamp(), waitToken.getRank());
    }


}
