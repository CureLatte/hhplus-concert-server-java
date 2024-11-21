package io.hhplus.tdd.hhplusconcertjava.payment.apllication;

import io.hhplus.tdd.hhplusconcertjava.concert.domain.entity.Reservation;
import io.hhplus.tdd.hhplusconcertjava.concert.domain.service.ConcertService;
import io.hhplus.tdd.hhplusconcertjava.outBox.domain.domain.OutBox;
import io.hhplus.tdd.hhplusconcertjava.outBox.domain.service.OutBoxService;
import io.hhplus.tdd.hhplusconcertjava.payment.domain.entity.Payment;
import io.hhplus.tdd.hhplusconcertjava.payment.domain.event.PaymentEventPublisher;
import io.hhplus.tdd.hhplusconcertjava.payment.domain.service.PaymentService;
import io.hhplus.tdd.hhplusconcertjava.payment.interfaces.dto.PostPayReservationResponseDto;
import io.hhplus.tdd.hhplusconcertjava.point.domain.entity.PointHistory;
import io.hhplus.tdd.hhplusconcertjava.point.domain.service.PointService;
import io.hhplus.tdd.hhplusconcertjava.user.domain.entity.User;
import io.hhplus.tdd.hhplusconcertjava.user.domain.service.UserService;
import io.hhplus.tdd.hhplusconcertjava.wait.domain.service.WaitService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static io.hhplus.tdd.hhplusconcertjava.common.kafka.Topic.DEACTIVATE_TOPIC;
import static io.hhplus.tdd.hhplusconcertjava.common.kafka.Topic.PAYMENT_TOPIC;

@Slf4j
@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PaymentFacade {
    PaymentService paymentService;
    UserService userService;
    ConcertService concertService;
    PointService pointService;
    WaitService waitService;
    PaymentEventPublisher paymentEvent;
    OutBoxService outBoxService;

    @Transactional
    public PostPayReservationResponseDto payReservation(Long userId, Long reservationId, int payAmount, String uuid){

        User user = this.userService.getUser(userId);

        Reservation reservation = this.concertService.getReservation(reservationId);

        PointHistory pointHistory = this.pointService.useUser(user, payAmount);

        Payment payment = this.paymentService.payReservation(user, reservation, pointHistory);

        // 대기열 삭제
        OutBox deactivateOutBox = this.outBoxService.init(OutBox.builder()
                        .eventKey("deactivate_" + uuid)
                        .payload(uuid)
                        .topic(DEACTIVATE_TOPIC)
                .build());

        this.paymentEvent.deleteActivateToken(deactivateOutBox);


        // 결제정보 전달
        OutBox paymentOutBox = this.outBoxService.init(OutBox.builder()
                .eventKey("payment_" +payment.id) // partition 영향을 받음
                .payload("paymentId: " + payment.id) // value 생성 이전에 insert 되야함
                .topic(PAYMENT_TOPIC)
                .build());

        this.paymentEvent.sendOrderInfo(paymentOutBox);

        // this.waitService.deleteActivateToken(uuid);

        return new PostPayReservationResponseDto(payment != null);

    }

}
