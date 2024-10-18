package io.hhplus.tdd.hhplusconcertjava.user.domain.repository;

import io.hhplus.tdd.hhplusconcertjava.user.domain.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository {
    public User findById(long id);
}
