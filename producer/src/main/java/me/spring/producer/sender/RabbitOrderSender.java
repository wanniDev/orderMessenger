package me.spring.producer.sender;

import static me.spring.producer.constant.OrderMessageStatus.*;

import java.time.LocalDateTime;

import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.spring.model.Order;
import me.spring.producer.repository.BrokerMessageLogRepository;

@Component
@RequiredArgsConstructor
@Slf4j
public class RabbitOrderSender implements AMQPSender<Order> {

	private final RabbitTemplate rabbitTemplate;

	private final BrokerMessageLogRepository brokerMessageLogRepository;

	@Override
	public void send(Order order) {
		// ConfirmCallback 인터페이스를 구현하면 메시지가 브로커에게 전송된 후 메시지가
		// 브로커 서버에 도달했는지 확인하기 위해 콜백이 트리거됩니다. 즉, Exchange 에 올바르게 도달했는지 여부만 확인됩니다.
		rabbitTemplate.setConfirmCallback(confirmCallback);
		// 메세지 고유의 ID
		CorrelationData correlationData = new CorrelationData(order.getMessageId());
		rabbitTemplate.convertAndSend("order-exchange", "order.ABC", order, correlationData);
	}

	final RabbitTemplate.ConfirmCallback confirmCallback = new RabbitTemplate.ConfirmCallback() {
		@Override
		public void confirm(CorrelationData correlationData, boolean ack, String cause) {
			log.error("correlationData: {}", correlationData);
			String messageId = correlationData.getId();
			if(ack) {
				brokerMessageLogRepository.updateBrokerMessageLogStatus(SUCCESS.getValue(), LocalDateTime.now(), messageId);
			} else {
				log.error("error while processing message! {}", cause);
			}
		}
	};
}
