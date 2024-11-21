package io.hhplus.tdd.hhplusconcertjava.outBox.domain.service;

import io.hhplus.tdd.hhplusconcertjava.common.error.BusinessError;
import io.hhplus.tdd.hhplusconcertjava.common.error.ErrorCode;
import io.hhplus.tdd.hhplusconcertjava.outBox.domain.domain.OutBox;
import io.hhplus.tdd.hhplusconcertjava.outBox.domain.repository.OutBoxRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class OutBoxServiceImpl implements OutBoxService{
    OutBoxRepository outBoxRepository;


    @Override
    public OutBox findById(Long id) {

        OutBox outBox = outBoxRepository.findById(id);
        if(outBox == null){
            throw new BusinessError(ErrorCode.NOT_FOUND_OUT_BOX_ERROR.getStatus(), ErrorCode.NOT_FOUND_OUT_BOX_ERROR.getMessage());
        }

        return this.outBoxRepository.findById(id);
    }

    @Override
    public OutBox init(OutBox outBox) {
        return this.outBoxRepository.save(outBox);
    }

    @Override
    public OutBox receive(OutBox outBox) {

        outBox.receive();

        return this.outBoxRepository.save(outBox);
    }

    @Override
    public OutBox success(OutBox outBox) {

        outBox.success();

        return this.outBoxRepository.save(outBox);
    }
}
