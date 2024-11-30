package io.hhplus.tdd.hhplusconcertjava.point.infrastructure.entity;

import io.hhplus.tdd.hhplusconcertjava.point.domain.entity.Point;
import io.hhplus.tdd.hhplusconcertjava.point.domain.entity.PointHistory;
import jakarta.persistence.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Slf4j
@Entity
@Table(name="point_history")
@EntityListeners(AuditingEntityListener.class)
public class PointHistoryEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="point_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private PointEntity point;

    @Column(name="status")
    private String status;

    @Column(name="point_amount")
    private int pointAmount;



    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;

    public PointHistory toDomain(){
        log.info("Point: ", this.point.toString());
        return PointHistory.builder()
                .id(this.id)
                .status(PointHistory.PointStatus.valueOf(this.status))
                .point(this.point.toDomain())
                .pointAmount(this.pointAmount)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .build();
    }

    public static PointHistoryEntity fromDomain(PointHistory pointHistory){
        PointHistoryEntity entity = new PointHistoryEntity();

        entity.id=pointHistory.getId();
        entity.point=PointEntity.fromDomain(pointHistory.getPoint());
        entity.status=pointHistory.getStatus().name();
        entity.pointAmount=pointHistory.getPointAmount();
        entity.createdAt=pointHistory.getCreatedAt();
        entity.updatedAt=pointHistory.getUpdatedAt();
        return entity;


    }

}
