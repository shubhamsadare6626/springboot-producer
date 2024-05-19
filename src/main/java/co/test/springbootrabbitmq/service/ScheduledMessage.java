package co.test.springbootrabbitmq.service;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import co.test.springbootrabbitmq.config.SystemConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScheduledMessage {
	
	private final SystemConfig systemConfig;
	private final AmqpTemplate amqpTemplate;

	@Scheduled(cron="*/15 * * * * *") //after each 15 sec
	public void messageProducer() {
		String message = "Scheduled Producer : Hellooo....";
		
		log.info("Scheduled Message sending -> {}", message);
		
		//First queue
		amqpTemplate.convertAndSend(systemConfig.getExchange(), 
									systemConfig.getRoutingkey(), 
									message);
	}
}
