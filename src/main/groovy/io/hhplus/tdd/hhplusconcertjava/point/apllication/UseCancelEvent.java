package io.hhplus.tdd.hhplusconcertjava.point.apllication;

import io.hhplus.tdd.hhplusconcertjava.point.domain.entity.PointHistory;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UseCancelEvent {
    private PointHistory pointHistory;
}
