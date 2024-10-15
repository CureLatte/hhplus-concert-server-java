package io.hhplus.tdd.hhplusconcertjava.point.interfaces;

import io.hhplus.tdd.hhplusconcertjava.point.interfaces.dto.GetPointResponseDto;
import io.hhplus.tdd.hhplusconcertjava.point.interfaces.dto.PostPointChargeRequestDto;
import io.hhplus.tdd.hhplusconcertjava.point.interfaces.dto.PostPointChargeResponseDto;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class PointController implements IPointController{


    @Override
    @GetMapping("/point/{userId}")
    public GetPointResponseDto getPoint(@PathVariable("userId") String userId) {

        return new GetPointResponseDto(1000);
    }

    @Override
    @PostMapping("/point/charge")
    public PostPointChargeResponseDto getPointCharge(@RequestBody PostPointChargeRequestDto requestDto) {
        return new PostPointChargeResponseDto(2000);
    }
}

