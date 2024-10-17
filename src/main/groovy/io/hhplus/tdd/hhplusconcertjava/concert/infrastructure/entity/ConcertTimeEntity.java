package io.hhplus.tdd.hhplusconcertjava.concert.infrastructure.entity;


import io.hhplus.tdd.hhplusconcertjava.concert.domain.entity.ConcertTime;
import jakarta.persistence.*;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name="concert_time")
@EntityListeners(AuditingEntityListener.class)
public class ConcertTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "concert_id")
    private ConcertEntity concert;

    @Column(name="start_time")
    private LocalDateTime start_time;

    @Column(name = "end_time")
    private LocalDateTime end_time;

    @Column(name="max_cnt")
    private int max_cnt;

    @Column(name="left_cnt")
    private int left_cnt;

    @Column(name="price")
    private int price;

    @Column(name="status")
    private String status;


    public ConcertTime toDomain(){
        return ConcertTime.builder()
                .id(this.id)
                .concert(this.concert.toDomain())
                .startTime(this.start_time)
                .endTime(this.end_time)
                .maxCnt(this.max_cnt)
                .leftCnt(this.left_cnt)
                .price(this.price)
                .status(ConcertTime.ConcertTimeStatus.valueOf(this.status))
                .build();
    }


    public static ConcertTimeEntity fromDomain(ConcertTime concertTime){
        ConcertTimeEntity entity = new ConcertTimeEntity();
        entity.id=concertTime.getId();
        entity.concert = ConcertEntity.fromDomain(concertTime.getConcert());
        entity.start_time = concertTime.getStartTime();
        entity.end_time = concertTime.getEndTime();
        entity.max_cnt = concertTime.getMaxCnt();
        entity.left_cnt = concertTime.getLeftCnt();
        entity.price = concertTime.getPrice();
        entity.status = concertTime.getStatus().name();

        return entity;
    }
}
