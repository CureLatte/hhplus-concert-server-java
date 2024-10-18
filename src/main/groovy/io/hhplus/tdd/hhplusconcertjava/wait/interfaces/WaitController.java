package io.hhplus.tdd.hhplusconcertjava.wait.interfaces;

import io.hhplus.tdd.hhplusconcertjava.common.annotaion.CustomCheck;
import io.hhplus.tdd.hhplusconcertjava.wait.application.WaitFacade;
import io.hhplus.tdd.hhplusconcertjava.wait.domain.entity.WaitQueue;
import io.hhplus.tdd.hhplusconcertjava.wait.interfaces.dto.GetTokenResponseDto;
import io.swagger.v3.oas.annotations.tags.Tag;
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
    public GetTokenResponseDto getWaitToken(@RequestHeader Map<String, String> header, @RequestParam Map<String, String> params) {

        String userId = header.get("Authorization");

        String token = header.get("token");

        WaitQueue waitQueue = this.waitFacade.getWaitToken(token,userId);

        return new GetTokenResponseDto(waitQueue.getUuid(), waitQueue.getStatus().name());
    }
}
