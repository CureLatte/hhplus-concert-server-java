package io.hhplus.tdd.hhplusconcertjava.point.domain.entity;


import io.hhplus.tdd.hhplusconcertjava.user.domain.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class Point {
    public Long id;
    public User user;
    public Integer balance;



}
