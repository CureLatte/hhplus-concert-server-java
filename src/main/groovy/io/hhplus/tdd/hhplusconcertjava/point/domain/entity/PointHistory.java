package io.hhplus.tdd.hhplusconcertjava.point.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
public class PointHistory {
    public Long id;
    public PointStatus status;
    public Point point;
    public int pointAmount;
    public LocalDateTime createdAt;
    public LocalDateTime updatedAt;

    public enum PointStatus {
        CHARGE, USE
    }


}
