package io.hhplus.tdd.hhplusconcertjava.wait.interfaces;

import io.hhplus.tdd.hhplusconcertjava.wait.interfaces.dto.GetTokenResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Tag(name = "대기열 API", description = "대기열 관련 Controller 입니다.")
public interface IWaitController {

    
    public GetTokenResponseDto getWaitToken(@RequestHeader Map<String, String> header, @RequestParam Map<String, String> params);

}
