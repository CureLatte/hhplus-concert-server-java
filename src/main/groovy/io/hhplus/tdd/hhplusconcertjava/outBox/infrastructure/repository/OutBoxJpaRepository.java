package io.hhplus.tdd.hhplusconcertjava.outBox.infrastructure.repository;

import io.hhplus.tdd.hhplusconcertjava.outBox.domain.domain.OutBox;
import io.hhplus.tdd.hhplusconcertjava.outBox.domain.repository.OutBoxRepository;
import io.hhplus.tdd.hhplusconcertjava.outBox.infrastructure.entity.OutBoxEntity;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))

public class OutBoxJpaRepository implements OutBoxRepository {
    IOutBoxJpaRepository jpaRepository;


    @Override
    public OutBox findById(Long id) {
        OutBoxEntity outBoxEntity = jpaRepository.findById(id).orElse(null);
        if(outBoxEntity== null){
            return null;
        }

        return outBoxEntity.toDomain();
    }

    @Override
    public OutBox save(OutBox outBox) {

        OutBoxEntity outBoxEntity = this.jpaRepository.save(OutBoxEntity.fromDomain(outBox));

        return outBoxEntity.toDomain();
    }
}
