package io.hhplus.tdd.hhplusconcertjava.point.infrastructure.entity;


import io.hhplus.tdd.hhplusconcertjava.point.domain.entity.Point;
import io.hhplus.tdd.hhplusconcertjava.user.infrastructure.entity.UserEntity;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name="point")
@EntityListeners(AuditingEntityListener.class)
public class PointEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private UserEntity user;

    @Column(name="balance")
    private Integer balance;


    public Point toDomain(){
        return Point.builder()
                .id(this.id)
                .balance(this.balance)
                .user(this.user.toDomain())
                .build();
    }

    public static PointEntity fromDomain(Point point){
        PointEntity entity = new PointEntity();
        entity.id = point.getId();
        entity.balance = point.getBalance();
        entity.user = UserEntity.fromDomain(point.getUser());

        return entity;

    }
}
