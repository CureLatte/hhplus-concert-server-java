package io.hhplus.tdd.hhplusconcertjava.wait.interfaces;

import io.hhplus.tdd.hhplusconcertjava.wait.interfaces.dto.GetTokenResponseDto;
import org.springframework.web.bind.annotation.PathVariable;

public interface IWaitController {


    public GetTokenResponseDto getWaitToken(@PathVariable("userId") String userId);

}
