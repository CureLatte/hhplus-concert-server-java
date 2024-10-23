package io.hhplus.tdd.hhplusconcertjava.point.interfaces;

import io.hhplus.tdd.hhplusconcertjava.point.apllication.PointFacade;
import io.hhplus.tdd.hhplusconcertjava.point.interfaces.dto.GetPointResponseDto;
import io.hhplus.tdd.hhplusconcertjava.point.interfaces.dto.PostPointChargeRequestDto;
import io.hhplus.tdd.hhplusconcertjava.point.interfaces.dto.PostPointChargeResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PointController implements IPointController{

    PointFacade pointFacade;


    @Override
    @GetMapping("/point/{userId}")
    public GetPointResponseDto getPoint(@PathVariable("userId") String userId) {
        return this.pointFacade.getPoint(Long.valueOf(userId));
    }

    @Override
    @PostMapping("/point/charge")
    public PostPointChargeResponseDto getPointCharge(@RequestBody PostPointChargeRequestDto requestDto) {
        return this.pointFacade.chargePoint(requestDto.userId(), requestDto.point());
    }
}

