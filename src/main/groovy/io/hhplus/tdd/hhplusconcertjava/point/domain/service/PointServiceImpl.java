package io.hhplus.tdd.hhplusconcertjava.point.domain.service;

import io.hhplus.tdd.hhplusconcertjava.point.domain.repository.PointHistoryRepository;
import io.hhplus.tdd.hhplusconcertjava.point.domain.repository.PointRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PointServiceImpl implements PointService {

    PointRepository pointRepository;
    PointHistoryRepository pointHistoryRepository;


}
