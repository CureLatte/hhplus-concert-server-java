package io.hhplus.tdd.hhplusconcertjava.point.interfaces;

import io.hhplus.tdd.hhplusconcertjava.point.interfaces.dto.GetPointResponseDto;
import io.hhplus.tdd.hhplusconcertjava.point.interfaces.dto.PostPointChargeRequestDto;
import io.hhplus.tdd.hhplusconcertjava.point.interfaces.dto.PostPointChargeResponseDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PathVariable;

@Tag(name="Point API", description = "포인트 관련 API 입니다.")
public interface IPointController {
    public GetPointResponseDto getPoint(@PathVariable String userId);

    public PostPointChargeResponseDto getPointCharge(PostPointChargeRequestDto requestDto);
}
