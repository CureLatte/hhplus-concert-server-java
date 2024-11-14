package io.hhplus.tdd.hhplusconcertjava.point.domain.service;

import io.hhplus.tdd.hhplusconcertjava.point.domain.entity.Point;
import io.hhplus.tdd.hhplusconcertjava.point.domain.entity.PointHistory;
import io.hhplus.tdd.hhplusconcertjava.point.domain.repository.PointHistoryRepository;
import io.hhplus.tdd.hhplusconcertjava.point.domain.repository.PointRepository;
import io.hhplus.tdd.hhplusconcertjava.user.domain.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PointServiceImpl implements PointService {

    PointRepository pointRepository;
    PointHistoryRepository pointHistoryRepository;


    @Override
    public Point getPoint(User user) {

        Point point = pointRepository.findByUser(user);
        if(point == null){
            point = pointRepository.save(Point.builder()
                            .user(user)
                            .balance(0)
                    .build());
        }

        return point;
    }

    @Override
    public PointHistory charge(Point point, int chargePoint) {

        point.charge(chargePoint);
        this.pointRepository.save(point);

        // pointHistory
        PointHistory pointHistory = this.pointHistoryRepository.save(PointHistory.builder()
                .pointAmount(chargePoint)
                .status(PointHistory.PointStatus.CHARGE)
                .point(point)
                .build());


        return pointHistory;
    }

    @Transactional
    @Override
    public PointHistory use(Point point, int usePoint) {
        point.use(usePoint);

        this.pointRepository.save(point);

        PointHistory pointHistory = this.pointHistoryRepository.save(PointHistory.builder()
                        .pointAmount(usePoint)
                        .status(PointHistory.PointStatus.USE)
                        .point(point)
                        .build());


        return pointHistory;
    }


}
