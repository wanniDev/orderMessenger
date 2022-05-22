package me.spring.producer.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import me.spring.model.BrokerMessageLog;
import me.spring.model.Order;
import me.spring.producer.repository.BrokerMessageLogRepository;
import me.spring.producer.repository.OrderRepository;
import me.spring.producer.sender.RabbitOrderSender;

@Service
@RequiredArgsConstructor
public class OrderService {
	private final OrderRepository orderRepository;

	private final BrokerMessageLogRepository brokerMessageLogRepository;

	private final RabbitOrderSender orderSender;

	private final ObjectMapper objectMapper;

	public void createOrder(Order order) {
		try {
			LocalDateTime now = LocalDateTime.now();
			orderRepository.save(order);
			String orderMessage = objectMapper.writeValueAsString(order);
			BrokerMessageLog brokerMessageLog = BrokerMessageLog.builder()
				.message(orderMessage)
				.createTime(now)
				.updateTime(now)
				.build();
			brokerMessageLogRepository.save(brokerMessageLog);
			orderSender.send(order);
		} catch (JsonProcessingException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}
}
