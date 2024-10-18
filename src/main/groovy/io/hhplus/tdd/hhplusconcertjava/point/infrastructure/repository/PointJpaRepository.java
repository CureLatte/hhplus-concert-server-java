package io.hhplus.tdd.hhplusconcertjava.point.infrastructure.repository;

import io.hhplus.tdd.hhplusconcertjava.point.domain.entity.Point;
import io.hhplus.tdd.hhplusconcertjava.point.domain.repository.PointRepository;
import io.hhplus.tdd.hhplusconcertjava.point.infrastructure.entity.PointEntity;
import io.hhplus.tdd.hhplusconcertjava.user.domain.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PointJpaRepository implements PointRepository {
    IPointJpaRepository jpaRepository;


    @Override
    public Point findByUser(User user) {
        PointEntity pointEntity = this.jpaRepository.findByUserId(user.id);

        if(pointEntity == null){
            return null;
        }

        return pointEntity.toDomain();
    }

    @Override
    public Point save(Point point) {
        PointEntity entity = this.jpaRepository.save(PointEntity.fromDomain(point));

        return entity.toDomain();
    }
}
