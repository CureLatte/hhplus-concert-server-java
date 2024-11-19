package io.hhplus.tdd.hhplusconcertjava.point.domain.repository;

import io.hhplus.tdd.hhplusconcertjava.point.domain.entity.Point;
import io.hhplus.tdd.hhplusconcertjava.user.domain.entity.User;

public interface PointRepository {

    public Point findByUser(User user);

    public Point save(Point point);

    public void deleteAll();
}
