package io.hhplus.tdd.hhplusconcertjava.concert.application;

import io.hhplus.tdd.hhplusconcertjava.concert.domain.service.ConcertService;
import io.hhplus.tdd.hhplusconcertjava.concert.domain.service.IConcertService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ConcertFacade {


    IConcertService concertService;



}
