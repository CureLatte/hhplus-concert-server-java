package io.hhplus.tdd.hhplusconcertjava.concert.infrastructure.entity;


import io.hhplus.tdd.hhplusconcertjava.concert.domain.entity.ConcertSeat;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Slf4j
@Entity
@Table(name="concert_seat")
@EntityListeners(AuditingEntityListener.class)
public class ConcertSeatEntity {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="concert_time_id", foreignKey= @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private ConcertTimeEntity concertTime;

    @Column(name="status")
    private String status;

    @Column(name="number")
    private String number;

    @Column(name="uuid")
    private String uuid;

    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;


    @Version
    @ColumnDefault("0")
    private Integer version;

    public ConcertSeat toDomain(){
        return ConcertSeat.builder()
                .id(this.id)
                .number(this.number)
                .concertTime(this.concertTime.toDomain())
                .status(ConcertSeat.ConcertSeatStatus.valueOf(this.status))
                .uuid(this.uuid)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                 .version(this.version)
                .build();
    }


    public static ConcertSeatEntity fromDomain(ConcertSeat domain){
        ConcertSeatEntity entity = new ConcertSeatEntity();
        entity.id = domain.getId();
        entity.concertTime = ConcertTimeEntity.fromDomain(domain.getConcertTime());
        entity.status = domain.getStatus().name();
        entity.number = domain.getNumber();
        entity.uuid = domain.getUuid();
        entity.createdAt = domain.getCreatedAt();
        entity.updatedAt = domain.getUpdatedAt();
         entity.version = domain.getVersion();

        return entity;

    }

}
