package io.hhplus.tdd.hhplusconcertjava.concert.infrastructure.entity;

import io.hhplus.tdd.hhplusconcertjava.common.entity.BaseEntity;
import io.hhplus.tdd.hhplusconcertjava.concert.domain.entity.ConcertCast;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name="concert_cast")
public class ConcertCastEntity extends BaseEntity {

    private String name;

    public ConcertCast toDomain(){

        return  ConcertCast.builder()
                .id(this.getId())
                .name(name)
                .createdAt(this.getCreatedAt())
                .updatedAt(this.getUpdatedAt())
                .deletedAt(this.getDeletedAt())
                .build();

    }

    public static ConcertCastEntity fromDomain(ConcertCast domain){
        ConcertCastEntity entity = new ConcertCastEntity();
        entity.name=domain.getName();

        entity.setId(domain.getId());
        entity.setDeletedAt(domain.getDeletedAt());
        entity.setUpdatedAt(domain.getUpdatedAt());
        entity.setCreatedAt(domain.getCreatedAt());

        return entity;

    }
}
