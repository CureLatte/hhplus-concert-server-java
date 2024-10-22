package io.hhplus.tdd.hhplusconcertjava.user.domain.service;

import io.hhplus.tdd.hhplusconcertjava.common.BusinessError;
import io.hhplus.tdd.hhplusconcertjava.user.domain.entity.User;
import io.hhplus.tdd.hhplusconcertjava.user.domain.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService implements IUserService{

    UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getUser(long id) throws BusinessError {

        User user = this.userRepository.findById(id);

        if(user == null) {
            throw new BusinessError(400, "존재하지 않은 유저");
        }

        return user;
    }
}
