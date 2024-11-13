package io.hhplus.tdd.hhplusconcertjava.concert.infrastructure.entity;

import io.hhplus.tdd.hhplusconcertjava.common.entity.BaseEntity;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="concert_cast_list")
public class ConcertCastListEntity extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "concert_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    ConcertEntity concert;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="concert_cast_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    ConcertCastEntity concertCast;

}
