package io.hhplus.tdd.hhplusconcertjava.wait.application;

import io.hhplus.tdd.hhplusconcertjava.wait.domain.service.WaitService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@Builder
public class DeleteActivateTokenEvent {
    String uuid;
}
