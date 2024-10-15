package io.hhplus.tdd.hhplusconcertjava.point.interfaces;

import io.hhplus.tdd.hhplusconcertjava.point.interfaces.dto.GetPointResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class PointController implements IPointController{


    @Override
    @GetMapping("/point/{userId}")
    public GetPointResponseDto getPoint(@PathVariable("userId") String userId) {

        return new GetPointResponseDto(1000);
    }
}

