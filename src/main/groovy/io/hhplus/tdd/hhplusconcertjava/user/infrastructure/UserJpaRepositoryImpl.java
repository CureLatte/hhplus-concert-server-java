package io.hhplus.tdd.hhplusconcertjava.user.infrastructure;

import io.hhplus.tdd.hhplusconcertjava.user.domain.entity.User;
import io.hhplus.tdd.hhplusconcertjava.user.domain.repository.UserRepository;
import io.hhplus.tdd.hhplusconcertjava.user.infrastructure.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
public class UserJpaRepositoryImpl implements UserRepository {
    private UserJpaRepository userJpaRepository;

    @Override
    public User findById(long id){

        UserEntity userEntity = userJpaRepository.findById(id).orElse(null);

        if(userEntity == null){
            return null;
        }

        return userEntity.toDomain();
    }

}
