package io.hhplus.tdd.hhplusconcertjava.unit.outbox;


import io.hhplus.tdd.hhplusconcertjava.common.error.BusinessError;
import io.hhplus.tdd.hhplusconcertjava.common.error.ErrorCode;
import io.hhplus.tdd.hhplusconcertjava.outBox.domain.domain.OutBox;
import io.hhplus.tdd.hhplusconcertjava.outBox.domain.repository.OutBoxRepository;
import io.hhplus.tdd.hhplusconcertjava.outBox.domain.service.OutBoxService;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
public class TestOutBox {

    @Test
    public void 수신_변경시_이미_변경시_에러(){
        // GIVEN
        OutBox outBox = OutBox
                .builder()
                .id(1L)
                .topic("topic")
                .status(OutBox.OutBoxStatus.RECEIVE)
                .key("key")
                .payload("payload")
                .deletedAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .deletedAt(null)
                .build();


        // WHEN
        BusinessError businessError = assertThrows(BusinessError.class, outBox::receive);

        // THEN
        assertEquals(ErrorCode.ALREADY_RECEIVE_OUT_BOX_ERROR.getMessage(), businessError.getMessage());

    }

    @Test
    public void 수신_상태_변경__성공(){
        // GIVEN
        OutBox outBox = OutBox.builder()
                .status(OutBox.OutBoxStatus.INIT)
                .build();

        // WHEN
        outBox.receive();

        // THEN
        assertEquals(OutBox.OutBoxStatus.RECEIVE, outBox.getStatus());
    }


    @Test
    public void 성공_상태_변경__성공(){
        // GIVEN
        OutBox outBox = OutBox.builder()
                .status(OutBox.OutBoxStatus.INIT)
                .build();

        // WHEN
        outBox.success();

        // THEN
        assertEquals(OutBox.OutBoxStatus.SUCCESS, outBox.getStatus());
    }


}
