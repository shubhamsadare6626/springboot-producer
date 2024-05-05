package co.test.springbootrabbitmq.producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import co.test.springbootrabbitmq.dto.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
@RequiredArgsConstructor
public class MessageProducer {
	
	@Value("${rabbitmq.exchange}")
	private String exchange;

	@Value("${rabbitmq.test.routingkey}")
	private String routingkey;
	
	@Value("${rabbitmq.test2.routingkey}")
	private String routingkey2;
	
	@Value("${rabbitmq.json.exchange}")
	private String jsonExchange;
	
	@Value("${rabbitmq.json.routingkey}")
	private String jsonRoutingkey;
	
	@Value("${rabbitmq.json.email-routingkey}")
	private String jsonEmailRoutingkey;

	private final RabbitTemplate rabbitTemplate;
	
	@Scheduled(fixedDelay = 15000) //after each 15 sec
	public void messageProducer() {
		String message = "Hellooo....";
//		log.info("Scheduled Message sending -> {}", message);
//		rabbitTemplate.convertAndSend(exchange, routingkey, message);//First queue
//		rabbitTemplate.convertAndSend(exchange, routingkey2, message);//Second queue
	}
	
	public void messageSending(String message) {
		log.info("Message sending -> {}", message);
		rabbitTemplate.convertAndSend(exchange, routingkey, message);//First queue
		rabbitTemplate.convertAndSend(exchange, routingkey2, message);//Second queue
	}
	
	public void jsonObjectSending(User user) {
		log.info("Message sending -> {}", user.toString());
		// For Json queue
//		rabbitTemplate.convertAndSend(jsonExchange, jsonRoutingkey, user);
		
		// For Json as well as email queue
		rabbitTemplate.convertAndSend(jsonExchange, jsonEmailRoutingkey, user);

		log.debug("Message sent -> {}", user.toString());
	}
}
