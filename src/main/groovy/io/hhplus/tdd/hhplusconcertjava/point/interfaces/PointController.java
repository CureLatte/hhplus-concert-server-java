package io.hhplus.tdd.hhplusconcertjava.point.interfaces;

import io.hhplus.tdd.hhplusconcertjava.common.error.BusinessError;
import io.hhplus.tdd.hhplusconcertjava.point.apllication.PointFacade;
import io.hhplus.tdd.hhplusconcertjava.point.interfaces.dto.GetPointResponseDto;
import io.hhplus.tdd.hhplusconcertjava.point.interfaces.dto.PostPointChargeRequestDto;
import io.hhplus.tdd.hhplusconcertjava.point.interfaces.dto.PostPointChargeResponseDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PointController implements IPointController{

    PointFacade pointFacade;


    @Override
    @GetMapping("/point/{userId}")
    public GetPointResponseDto getPoint(@PathVariable("userId") String userId) {
        try {

            return this.pointFacade.getPoint(Long.valueOf(userId));
        } catch (BusinessError businessError) {
            log.error("[pointFacade] getPoint Error: {}", businessError.getMessage());
            throw businessError;
        }


    }

    @Override
    @PostMapping("/point/charge")
    public PostPointChargeResponseDto getPointCharge(@RequestBody PostPointChargeRequestDto requestDto) {
        try {
            return this.pointFacade.chargePoint(requestDto.userId(), requestDto.point());
        } catch (BusinessError businessError) {
            log.error("[pointFacade] chargePoint Error: {}", businessError.getMessage());
            throw businessError;
        }
    }
}

