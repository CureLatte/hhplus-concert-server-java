package io.hhplus.tdd.hhplusconcertjava.concert.infrastructure.entity;

import io.hhplus.tdd.hhplusconcertjava.common.entity.BaseEntity;
import io.hhplus.tdd.hhplusconcertjava.concert.domain.entity.ConcertPlace;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name="concert_place")
public class ConcertPlaceEntity extends BaseEntity {

    private String name;
    private String address;

    private Double latitude;
    private Double longitude;



    public ConcertPlace toDomain(){
        return ConcertPlace.builder()
                .id(this.getId())
                .address(this.address)
                .name(this.name)
                .latitude(this.latitude)
                .longitude(this.longitude)
                .createdAt(this.getCreatedAt())
                .updatedAt(this.getUpdatedAt())
                .deletedAt(this.getDeletedAt())
                .build();
    }


    public ConcertPlaceEntity fromDomain(ConcertPlace domain){

        ConcertPlaceEntity entity = new ConcertPlaceEntity();
        entity.setId(domain.getId());
        entity.setCreatedAt(domain.getCreatedAt());
        entity.setUpdatedAt(domain.getUpdatedAt());
        entity.setDeletedAt(domain.getDeletedAt());

        entity.address = domain.getAddress();
        entity.name = domain.getName();
        entity.latitude = domain.getLatitude();

        return entity;


    }
}
