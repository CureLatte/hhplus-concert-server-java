package io.hhplus.tdd.hhplusconcertjava.concert.infrastructure.entity;

import io.hhplus.tdd.hhplusconcertjava.common.entity.BaseEntity;
import io.hhplus.tdd.hhplusconcertjava.concert.domain.entity.ConcertViewRank;
import jakarta.persistence.*;

@Entity
@Table(name="concert_view_rank")
public class ConcertViewRankEntity extends BaseEntity {

    private String name;

    @Column(name="limit_age")
    private Integer limitAge;


    public ConcertViewRank toDomain(){
        return ConcertViewRank.builder()
                .id(this.getId())
                .name(this.name)
                .limitAge(this.limitAge)
                .createdAt(this.getCreatedAt())
                .updatedAt(this.getUpdatedAt())
                .deletedAt(this.getDeletedAt())
                .build();
    }


    public static ConcertViewRankEntity fromDomain(ConcertViewRank concertViewRank){
        ConcertViewRankEntity entity = new ConcertViewRankEntity();

        entity.setId(concertViewRank.getId());
        entity.setCreatedAt(concertViewRank.getCreatedAt());
        entity.setUpdatedAt(concertViewRank.getUpdatedAt());
        entity.setDeletedAt(concertViewRank.getDeletedAt());
        entity.name = concertViewRank.getName();
        entity.limitAge = concertViewRank.getLimitAge();

        return entity;

    }
}
