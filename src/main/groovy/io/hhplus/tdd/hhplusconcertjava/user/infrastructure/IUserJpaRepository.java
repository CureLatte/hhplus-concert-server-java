package io.hhplus.tdd.hhplusconcertjava.user.infrastructure;

import io.hhplus.tdd.hhplusconcertjava.user.infrastructure.entity.UserEntity;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Hidden
public interface IUserJpaRepository extends JpaRepository<UserEntity, Long> {

    @Query(value = """
        select u from UserEntity u where u.id= :userId
    """)
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public Optional<UserEntity> findByIdForUpdate(@Param("userId") Long userId);

    @Transactional
    @Query(value = """
        delete from user
    """, nativeQuery = true)
    @Modifying
    public void clearTable();
}
