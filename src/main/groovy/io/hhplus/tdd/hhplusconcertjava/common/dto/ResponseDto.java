package io.hhplus.tdd.hhplusconcertjava.common.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor(staticName = "of")
public class ResponseDto<D> {
    public boolean success;
    public D data;
}
