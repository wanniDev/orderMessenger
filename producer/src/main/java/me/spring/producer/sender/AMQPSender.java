package me.spring.producer.sender;

public interface AMQPSender<T> {
	void send(T t);
}
