package me.spring.producer.sender;

public interface Sender<T> {
	public void send(T t);
}
