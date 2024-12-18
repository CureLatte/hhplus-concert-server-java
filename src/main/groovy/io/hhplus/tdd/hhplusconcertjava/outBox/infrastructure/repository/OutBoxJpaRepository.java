package io.hhplus.tdd.hhplusconcertjava.outBox.infrastructure.repository;

import io.hhplus.tdd.hhplusconcertjava.outBox.domain.domain.OutBox;
import io.hhplus.tdd.hhplusconcertjava.outBox.domain.repository.OutBoxRepository;
import io.hhplus.tdd.hhplusconcertjava.outBox.infrastructure.entity.OutBoxEntity;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Component
@Transactional
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
    public OutBox findByOutBox(OutBox outBox) {
        log.info("outBOx: {}", outBox.toString());

        List<OutBoxEntity> outBoxEntityList = jpaRepository.findByTopicKeyPayload(outBox.topic, outBox.eventKey, outBox.payload);

        if(outBoxEntityList.size() == 0 ){
            return null;
        }

        return outBoxEntityList.get(0).toDomain();
    }

    @Override
    public OutBox save(OutBox outBox) {

        OutBoxEntity outBoxEntity = this.jpaRepository.save(OutBoxEntity.fromDomain(outBox));

        return outBoxEntity.toDomain();
    }

    @Override
    public List<OutBox> findByStatus(OutBox.OutBoxStatus status) {

        List<OutBoxEntity> outBoxEntityList = this.jpaRepository.findByStatus(status.name());

        return outBoxEntityList.stream().map(OutBoxEntity::toDomain).toList();
    }
}
