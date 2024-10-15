package io.hhplus.tdd.hhplusconcertjava.user.domain.entity;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class User {
    public Long id;
    public String name;
}
