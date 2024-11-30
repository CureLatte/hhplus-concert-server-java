package io.hhplus.tdd.hhplusconcertjava.common.kafka;

public class Topic {
    Topic(){}; // 인스턴스화 방지

    public static String PAYMENT_TOPIC = "payment";
    public static String DEACTIVATE_TOPIC = "deactivateToken";
}
