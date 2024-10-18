package io.hhplus.tdd.hhplusconcertjava.user.infrastructure;

import io.hhplus.tdd.hhplusconcertjava.user.infrastructure.entity.UserEntity;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Hidden
public interface IUserJpaRepository extends JpaRepository<UserEntity, Long> {
}
