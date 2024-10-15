package io.hhplus.tdd.hhplusconcertjava.point.interfaces;

import io.hhplus.tdd.hhplusconcertjava.point.interfaces.dto.GetPointResponseDto;
import org.springframework.web.bind.annotation.PathVariable;

public interface IPointController {
    public GetPointResponseDto getPoint(@PathVariable String userId);
}
