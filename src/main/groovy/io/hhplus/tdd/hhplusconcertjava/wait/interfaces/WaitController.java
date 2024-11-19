package io.hhplus.tdd.hhplusconcertjava.wait.interfaces;

import io.hhplus.tdd.hhplusconcertjava.common.error.BusinessError;
import io.hhplus.tdd.hhplusconcertjava.wait.application.WaitFacade;
import io.hhplus.tdd.hhplusconcertjava.wait.domain.entity.WaitQueue;
import io.hhplus.tdd.hhplusconcertjava.wait.domain.entity.WaitToken;
import io.hhplus.tdd.hhplusconcertjava.wait.interfaces.dto.GetTokenResponseDto;
import io.hhplus.tdd.hhplusconcertjava.wait.interfaces.dto.GetWaitTokenResponseDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@AllArgsConstructor
public class WaitController implements IWaitController{

    WaitFacade waitFacade;

    @Override
    @GetMapping("/wait")
    public GetTokenResponseDto getWaitQueue(@RequestHeader Map<String, String> header, @RequestParam Map<String, String> params) {
        try {

            String userId = header.get("Authorization");

            String token = header.get("token");

            WaitQueue waitQueue = this.waitFacade.getWaitQueue(token,userId);



            return new GetTokenResponseDto(waitQueue.getUuid(), waitQueue.getStatus().name());

        } catch (BusinessError businessError){
            log.error("[WaitFacade] getWaitQueue Error: {}",businessError.getMessage());
            throw businessError;
        }


    }

    @Override
    @GetMapping("/waitToken")
    public GetTokenResponseDto getWaitToken(Map<String, String> header, Map<String, String> Params) {
        try {

            String userId = header.get("Authorization");

            String token = header.get("token");

            WaitQueue waitToken = this.waitFacade.getWaitToken(token);

            return new GetTokenResponseDto(waitToken.getUuid(), waitToken.getStatus().name());

        } catch (BusinessError businessError){

            log.error("[WaitFacade] getWaitToken Error: {}",businessError.getMessage());
            throw businessError;
        }

    }


}
