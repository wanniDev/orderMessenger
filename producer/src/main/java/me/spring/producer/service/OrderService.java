package me.spring.producer.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import me.spring.producer.repository.BrokerMessageLogRepository;
import me.spring.producer.repository.OrderRepository;
import me.spring.producer.sender.RabbitOrderSender;

@Service
@RequiredArgsConstructor
public class OrderService {
	private final OrderRepository orderRepository;

	private final BrokerMessageLogRepository brokerMessageLogRepository;

	private final RabbitOrderSender orderSender;
}
