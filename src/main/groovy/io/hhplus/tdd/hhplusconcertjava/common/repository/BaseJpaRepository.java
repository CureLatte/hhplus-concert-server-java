package io.hhplus.tdd.hhplusconcertjava.common.repository;

import io.hhplus.tdd.hhplusconcertjava.common.entity.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public class BaseJpaRepository<T > {
    JpaRepository<BaseEntity, Long> jpaRepository;

    public void clearTable(){
           this.jpaRepository.deleteAll();
    }
}
