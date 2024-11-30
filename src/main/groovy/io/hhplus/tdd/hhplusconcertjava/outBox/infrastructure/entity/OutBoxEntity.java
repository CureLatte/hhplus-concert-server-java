package io.hhplus.tdd.hhplusconcertjava.outBox.infrastructure.entity;


import io.hhplus.tdd.hhplusconcertjava.common.entity.BaseEntity;
import io.hhplus.tdd.hhplusconcertjava.outBox.domain.domain.OutBox;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Entity
@Table(name="out_box")
public class OutBoxEntity extends BaseEntity {
    @Column(name="payload")
    private String payload;

    @Column(name="event_key")
    private String eventKey;

    @Column(name="status")
    private String status;

    @Column(name="topic")
    private String topic;


    public OutBox toDomain(){
        return OutBox.builder()
                .id(this.getId())
                .eventKey(this.eventKey)
                .payload(this.payload)
                .status(OutBox.OutBoxStatus.valueOf(this.status))
                .topic(this.topic)
                .createdAt(this.getCreatedAt())
                .updatedAt(this.getUpdatedAt())
                .deletedAt(this.getDeletedAt())
                .build();
    }

    public static OutBoxEntity fromDomain(OutBox outBox){

        OutBoxEntity entity = new OutBoxEntity();
        entity.setId(outBox.getId());
        entity.setCreatedAt(outBox.getCreatedAt());
        entity.setUpdatedAt(outBox.getUpdatedAt());
        entity.setDeletedAt(outBox.getDeletedAt());

        entity.eventKey = outBox.getEventKey();
        entity.payload = outBox.getPayload();
        entity.status = outBox.getStatus().name();
        entity.topic = outBox.getTopic();
        log.info("entity: {}", entity);
        return entity;
    }

}
