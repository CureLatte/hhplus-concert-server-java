package io.hhplus.tdd.hhplusconcertjava.user.domain.entity;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class User {
    public Long id;
    public String name;
}
