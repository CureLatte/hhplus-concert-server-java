package io.hhplus.tdd.hhplusconcertjava.outBox.domain.service;

import io.hhplus.tdd.hhplusconcertjava.common.error.BusinessError;
import io.hhplus.tdd.hhplusconcertjava.common.error.ErrorCode;
import io.hhplus.tdd.hhplusconcertjava.common.filter.LogFilter;
import io.hhplus.tdd.hhplusconcertjava.outBox.domain.domain.OutBox;
import io.hhplus.tdd.hhplusconcertjava.outBox.domain.repository.OutBoxRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class OutBoxServiceImpl implements OutBoxService{
    private final LogFilter logFilter;
    OutBoxRepository outBoxRepository;
    KafkaTemplate<String, String> kafkaTemplate;

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

    @Override
    public void rePublish() {
        // init 내용 감지 + resend
        List<OutBox> getInitOutBoxList = this.outBoxRepository.findByStatus(OutBox.OutBoxStatus.INIT);

        List<OutBox> getReSendBoxList = this.outBoxRepository.findByStatus(OutBox.OutBoxStatus.RESEND);

        List<OutBox> getOutBoxList = Stream.concat(getInitOutBoxList.stream(), getReSendBoxList.stream()).collect(Collectors.toList());

        if(getOutBoxList.size() == 0){
            return;
        }
        log.info("getOutBoxList: {}", getOutBoxList.size());

        for(OutBox outBox : getOutBoxList){
            try {
                log.info("outBox: {}", outBox);
                kafkaTemplate.send(outBox.getTopic(), outBox.eventKey, outBox.payload);
                outBox.resend();
                this.outBoxRepository.save(outBox);
            } catch (Exception e){
                outBox.failure();
                this.outBoxRepository.save(outBox);
                log.error(e.getMessage());
            }

        }


    }
}
