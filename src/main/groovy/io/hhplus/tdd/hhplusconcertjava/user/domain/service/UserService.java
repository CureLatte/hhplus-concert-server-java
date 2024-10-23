package io.hhplus.tdd.hhplusconcertjava.user.domain.service;

import io.hhplus.tdd.hhplusconcertjava.common.error.BusinessError;
import io.hhplus.tdd.hhplusconcertjava.common.error.ErrorCode;
import io.hhplus.tdd.hhplusconcertjava.user.domain.entity.User;
import io.hhplus.tdd.hhplusconcertjava.user.domain.repository.UserRepository;
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
            throw new BusinessError(ErrorCode.NOT_FOUND_USER_ERROR.getStatus(), ErrorCode.NOT_FOUND_USER_ERROR.getMessage());
        }

        return user;
    }
}
