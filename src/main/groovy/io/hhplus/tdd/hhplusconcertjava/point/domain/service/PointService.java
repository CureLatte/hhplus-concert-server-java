package io.hhplus.tdd.hhplusconcertjava.point.domain.service;

import io.hhplus.tdd.hhplusconcertjava.point.domain.entity.Point;
import io.hhplus.tdd.hhplusconcertjava.user.domain.entity.User;

public interface PointService {

    public Point getPoint(User user);

    public Point charge(Point point, int chargePoint);

}
