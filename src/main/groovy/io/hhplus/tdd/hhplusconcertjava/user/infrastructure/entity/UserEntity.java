package io.hhplus.tdd.hhplusconcertjava.user.infrastructure.entity;

import io.hhplus.tdd.hhplusconcertjava.user.domain.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name="user")
public class UserEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name", nullable = false)
    private String name;

    public User toDomain(){
        return User.builder()
                .id(id)
                .name(name)
                .build();
    }

    public static UserEntity fromDomain(User user){
        UserEntity userEntity = new UserEntity();
        userEntity.setId(user.getId());
        userEntity.setName(user.getName());
        return userEntity;
    }
}
