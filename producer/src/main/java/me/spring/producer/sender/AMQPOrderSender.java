package me.spring.producer.sender;

import me.spring.model.Order;

public interface AMQPOrderSender {
	void send(Order order);
}
