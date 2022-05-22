package me.spring.consumer.receiver;

import java.io.IOException;
import java.util.Map;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;

import lombok.extern.slf4j.Slf4j;
import me.spring.model.Order;

@Component
@Slf4j
public class OrderReceiver {
	@RabbitListener(bindings = @QueueBinding(
			value = @Queue(value = "order-queue",durable = "true"),
			exchange = @Exchange(name="order-exchange",durable = "true",type = "topic"),
			key = "order.*"
		)
	)
	@RabbitHandler
	public void onOrderMessage(@Payload Order order, @Headers Map<String,Object> headers, Channel channel) throws
		IOException {
		// consumer
		log.info("---------메세지 수신 및 소비---------");
		log.info("주문 ID：{}", order.getId());

		 // Delivery Tag는 채널에서 전달된 메시지를 식별하는 데 사용됩니다. RabbitMQ는 Consumer 에게 메세지를 푸시할 때, Delivery Tag
		 // 를 첨부합니다.
		 // Consumer는 메시지가 확인 되었을 때 어떤 메시지가 확인되었는지 RabbitMQ 에게 알릴 수 있습니다.
		 // RabbitMQ 는 각 채널에서 각 메시지의 Delivery Tag가 1에서 증분되는걸 보장합니다.
		Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);

		/**
		 *  multiple 이 false 라면，현재 메시지가 확인되었음을 RabbitMQ 에게 알린다는 것을 의미합니다.
		 *  true 라면，첫 번째 매개변수에서 지정한 Delivery Tag 보다 작은 메시지를 추가로 확인하게 됩니다.
		 */
		boolean multiple = false;

		//ACK,确认一条消息已经被消费
		channel.basicAck(deliveryTag,multiple);
	}
}
