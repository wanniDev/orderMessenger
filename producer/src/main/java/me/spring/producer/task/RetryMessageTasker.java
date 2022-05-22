package me.spring.producer.task;

import static me.spring.producer.constant.OrderMessageStatus.*;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.spring.model.BrokerMessageLog;
import me.spring.model.Order;
import me.spring.producer.repository.BrokerMessageLogRepository;
import me.spring.producer.sender.AMQPOrderSender;

@Component
@Slf4j
@RequiredArgsConstructor
public class RetryMessageTasker {
	private final AMQPOrderSender orderSender;

	private final BrokerMessageLogRepository brokerMessageLogRepository;

	private final ObjectMapper objectMapper;

	@Scheduled(initialDelay = 5000, fixedDelay = 10000)
	public void reSend(){
		log.info("-----------예약된 작업 시작-----------");
		//pull status = 0 and timeout message
		LocalDateTime now = LocalDateTime.now();
		List<BrokerMessageLog> list = brokerMessageLogRepository.findBrokerMessageLogsByStatusAndNextRetryLessThanEqual(
			SENDING.getValue(), now);
		list.forEach(messageLog -> {
			if(messageLog.getTryCount() >= 3){
				//update fail message
				messageLog.updateBrokerMessageLogStatus(FAILURE.getValue(), now);
			} else {
				// resend
				messageLog.updateResend(now);
				Order reSendOrder = null;
				try {
					reSendOrder = objectMapper.readValue(messageLog.getMessage(), Order.class);
				} catch (JsonProcessingException e) {
					e.printStackTrace();
				}
				try {
					orderSender.send(reSendOrder);
				} catch (Exception e) {
					e.printStackTrace();
					log.error("-------------에러 발생-------------");
				}
			}
		});
	}
}
