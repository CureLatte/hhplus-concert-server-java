package io.hhplus.tdd.hhplusconcertjava.point.apllication;

import io.hhplus.tdd.hhplusconcertjava.point.domain.service.PointService;
import io.hhplus.tdd.hhplusconcertjava.point.domain.service.PointServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PointFacade {

    PointService pointService;



}
