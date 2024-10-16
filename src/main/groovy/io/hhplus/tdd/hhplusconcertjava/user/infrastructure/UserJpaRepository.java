package io.hhplus.tdd.hhplusconcertjava.user.infrastructure;

import io.hhplus.tdd.hhplusconcertjava.user.domain.entity.User;
import io.hhplus.tdd.hhplusconcertjava.user.domain.repository.UserRepository;
import io.hhplus.tdd.hhplusconcertjava.user.infrastructure.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserJpaRepository implements UserRepository {
    private IUserJpaRepository userJpaRepository;

    @Override
    public User findById(long id){

        UserEntity userEntity = userJpaRepository.findById(id).orElse(null);

        if(userEntity == null){
            return null;
        }

        return userEntity.toDomain();
    }

}
