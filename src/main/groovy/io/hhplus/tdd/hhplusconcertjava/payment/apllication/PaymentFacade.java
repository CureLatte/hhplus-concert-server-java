package io.hhplus.tdd.hhplusconcertjava.payment.apllication;

import io.hhplus.tdd.hhplusconcertjava.common.error.BusinessError;
import io.hhplus.tdd.hhplusconcertjava.concert.domain.entity.Reservation;
import io.hhplus.tdd.hhplusconcertjava.concert.domain.service.ConcertService;
import io.hhplus.tdd.hhplusconcertjava.payment.domain.entity.Payment;
import io.hhplus.tdd.hhplusconcertjava.payment.domain.service.PaymentService;
import io.hhplus.tdd.hhplusconcertjava.payment.interfaces.dto.PostPayReservationResponseDto;
import io.hhplus.tdd.hhplusconcertjava.point.domain.entity.Point;
import io.hhplus.tdd.hhplusconcertjava.point.domain.entity.PointHistory;
import io.hhplus.tdd.hhplusconcertjava.point.domain.service.PointService;
import io.hhplus.tdd.hhplusconcertjava.user.domain.entity.User;
import io.hhplus.tdd.hhplusconcertjava.user.domain.service.UserService;
import io.hhplus.tdd.hhplusconcertjava.wait.domain.entity.ActivateToken;
import io.hhplus.tdd.hhplusconcertjava.wait.domain.service.WaitService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PaymentFacade {
    PaymentService paymentService;
    UserService userService;
    ConcertService concertService;
    PointService pointService;
    WaitService waitService;

    @Transactional
    public PostPayReservationResponseDto payReservation(Long userId, Long reservationId, int payAmount, String uuid){

        User user = this.userService.getUser(userId);

        Reservation reservation = this.concertService.getReservation(reservationId);
        Point point = this.pointService.getPoint(user);

        PointHistory pointHistory = this.pointService.use(point, payAmount);

        Payment payment = this.paymentService.payReservation(user, reservation, pointHistory);


        this.waitService.deleteActivateToken(uuid);

        return new PostPayReservationResponseDto(payment != null);

    }

}
