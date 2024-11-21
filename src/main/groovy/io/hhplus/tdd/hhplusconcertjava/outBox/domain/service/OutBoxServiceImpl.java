package io.hhplus.tdd.hhplusconcertjava.outBox.domain.service;

import io.hhplus.tdd.hhplusconcertjava.common.error.BusinessError;
import io.hhplus.tdd.hhplusconcertjava.common.error.ErrorCode;
import io.hhplus.tdd.hhplusconcertjava.outBox.domain.domain.OutBox;
import io.hhplus.tdd.hhplusconcertjava.outBox.domain.repository.OutBoxRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public OutBox findByOutBox(OutBox outBox) {
        OutBox outBox1 = outBoxRepository.findByOutBox(outBox);

        if(outBox1 == null){
            throw new BusinessError(ErrorCode.NOT_FOUND_OUT_BOX_ERROR.getStatus(), ErrorCode.NOT_FOUND_OUT_BOX_ERROR.getMessage());
        }


        return outBox1;
    }

    @Override
    @Transactional
    public OutBox init(OutBox outBox) {
        outBox.init();
        return this.outBoxRepository.save(outBox);
    }

    @Override
    @Transactional
    public OutBox receive(OutBox outBox) {

        outBox.receive();

        return this.outBoxRepository.save(outBox);
    }

    @Override
    @Transactional
    public OutBox success(OutBox outBox) {

        outBox.success();

        return this.outBoxRepository.save(outBox);
    }
}
