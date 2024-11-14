package io.hhplus.tdd.hhplusconcertjava.point.domain.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UseCancelEvent {
    private PointHistory pointHistory;
}
