package io.hhplus.tdd.hhplusconcertjava.wait.infrastructure.entity;

import io.hhplus.tdd.hhplusconcertjava.wait.domain.entity.WaitQueue;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name="wait_queue")
@EntityListeners(AuditingEntityListener.class)
public class WaitQueueEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="uuid", nullable = false)
    private String uuid;

    @Column(name="user_id", nullable = true)
    private long userId;

    @Column(name="status")
    private String status;

    @Column(name="createdAt")
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(name="updatedAt")
    @LastModifiedDate
    private LocalDateTime updatedAt;

    public WaitQueue toDomain(){
        System.out.println("Entity ->> toDomain " + this.status);

        return WaitQueue.builder()
                .id(this.id)
                .uuid(this.uuid)
                .status(WaitQueue.WaitStatus.valueOf(this.status))
                .userId(this.userId)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .build();
    }

    public static WaitQueueEntity fromDomain(WaitQueue waitQueue){
        WaitQueueEntity entity = new WaitQueueEntity();
        entity.id=waitQueue.getId();
        entity.uuid=waitQueue.getUuid();
        entity.status=waitQueue.getStatus().name();
        entity.userId=waitQueue.getUserId();
        entity.createdAt=waitQueue.getCreatedAt();
        entity.updatedAt=waitQueue.getUpdatedAt();
        return entity;

    }

}
