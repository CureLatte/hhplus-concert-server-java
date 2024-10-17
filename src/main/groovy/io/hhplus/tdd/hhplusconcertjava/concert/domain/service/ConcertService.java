package io.hhplus.tdd.hhplusconcertjava.concert.domain.service;

import io.hhplus.tdd.hhplusconcertjava.concert.domain.repository.ConcertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor=@__(@Autowired))
public class ConcertService implements IConcertService {

    ConcertRepository concertRepository;


}
