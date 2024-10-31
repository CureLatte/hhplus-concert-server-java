package io.hhplus.tdd.hhplusconcertjava.user.domain.repository;

import io.hhplus.tdd.hhplusconcertjava.user.domain.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository {
    public User create(User user);

    public User findById(long id);

    public User findByIdForUpdate(long id);

    public User save(User user);

    public void clearTable();
}
