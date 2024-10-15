package io.hhplus.tdd.hhplusconcertjava.wait.interfaces;

import io.hhplus.tdd.hhplusconcertjava.wait.interfaces.dto.GetTokenResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WaitController implements IWaitController{

    @Override
    @GetMapping("/wait/{userId}")
    public GetTokenResponseDto getWaitToken(@PathVariable("userId") String userId) {

        System.out.println(userId);

        return new GetTokenResponseDto("uafafdf-asdfadsfads-asdfadf", "wait");
    }
}
