package io.hhplus.tdd.hhplusconcertjava.concert.infrastructure.entity;


import io.hhplus.tdd.hhplusconcertjava.concert.domain.entity.Concert;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name="concert")
@EntityListeners(AuditingEntityListener.class)
public class ConcertEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="title")
    private String title;

    @Column(name="status")
    private String status;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;


    public Concert toDomain(){
        return Concert.builder()
                .id(this.id)
                .title(this.title)
                .status(Concert.ConcertStatus.valueOf(this.status))
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .build();
    }

    public static ConcertEntity fromDomain(Concert concert){
        ConcertEntity entity = new ConcertEntity();
        entity.id = concert.getId();
        entity.title = concert.getTitle();
        entity.status = concert.getStatus().name();
        entity.createdAt = concert.getCreatedAt();
        entity.updatedAt = concert.getUpdatedAt();

        return entity;

    }
}