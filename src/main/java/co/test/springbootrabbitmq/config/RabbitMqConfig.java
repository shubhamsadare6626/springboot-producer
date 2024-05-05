package co.test.springbootrabbitmq.config;

import java.util.TimeZone;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Configuration
@EnableScheduling
public class RabbitMqConfig {

	@Bean
	public Queue testQueue() {
		return new Queue(queue);
	}
	
	@Value("${rabbitmq.test.queue}")
	private String queue;

	@Value("${rabbitmq.test2.queue}")
	private String queue2;

	@Value("${rabbitmq.exchange}")
	private String exchange;

	@Value("${rabbitmq.test.routingkey}")
	private String routingkey;

	@Value("${rabbitmq.test2.routingkey}")
	private String routingkey2;

	// can create multiple queue
	

	@Bean
	public Queue test2Queue() {
		return new Queue(queue2);
	}

	@Bean
	public DirectExchange directExchange() {
		return new DirectExchange(exchange);
	}

	// Binding queue with exchange using routing key
	@Bean
	public Binding binding() {
		return BindingBuilder.bind(testQueue()).to(directExchange()).with(routingkey);
	}

	@Bean
	public Binding test2binding() {
		return BindingBuilder.bind(test2Queue()).to(directExchange()).with(routingkey2);
	}

	@Bean
	public ObjectMapper objectMapper() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.setTimeZone(TimeZone.getDefault());
		mapper.enable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		mapper.setDateFormat(new StdDateFormat().withColonInTimeZone(true));
		mapper.setPropertyNamingStrategy(new PropertyNamingStrategies.SnakeCaseStrategy());
		return mapper;
	}

	@Bean
	public MessageConverter jsonMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}

	@Bean
	public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory) {
		RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(jsonMessageConverter());
		return rabbitTemplate;
	}
	
	// Spring boot Auto configuration -Configures
	// Connection Factory
	// Rabbit template

//	=====================================================================
//	Topic Exchange
	
	@Value("${rabbitmq.json.exchange}")
	private String jsonExchange;
	
	@Value("${rabbitmq.json.queue}")
	private String jsonQueue;
	
	@Value("${rabbitmq.json.email-queue}")
	private String jsonEmailQueue;
	
	@Value("${rabbitmq.json.routingkey}")
	private String jsonRoutingkey;
	
	@Value("${rabbitmq.json.email-routingkey}")
	private String jsonEmailRoutingkey;

	@Bean
	public Queue jsonQueue() {
		return new Queue(jsonQueue);
	}
	
	@Bean
	public Queue jsonEmailQueue() {
		return new Queue(jsonEmailQueue);
	}
	
	@Bean
	public TopicExchange topicExchange() {
		return new TopicExchange(jsonExchange);
	}
	
	@Bean
	public Binding jsonBinding() {
		return BindingBuilder.bind(jsonQueue()).to(topicExchange()).with(jsonRoutingkey);
	}

	@Bean
	public Binding jsonEmailBinding() {
		return BindingBuilder.bind(jsonEmailQueue()).to(topicExchange()).with(jsonEmailRoutingkey);
	}
	
}