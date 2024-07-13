package co.test.springbootrabbitmq.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.util.TimeZone;
import lombok.RequiredArgsConstructor;
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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class RabbitMqConfig {

  private final SystemConfig systemConfig;

  @Bean
  public Queue testQueue() {
    return new Queue(systemConfig.getQueue());
  }

  // can create multiple queue
  @Bean
  public Queue test2Queue() {
    return new Queue(systemConfig.getQueue2());
  }

  @Bean
  public DirectExchange directExchange() {
    return new DirectExchange(systemConfig.getExchange());
  }

  // Binding queue with exchange using routing key
  @Bean
  public Binding binding() {
    return BindingBuilder.bind(testQueue()).to(directExchange()).with(systemConfig.getRoutingkey());
  }

  @Bean
  public Binding test2binding() {
    return BindingBuilder.bind(test2Queue())
        .to(directExchange())
        .with(systemConfig.getRoutingkey2());
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

  //	====================== Topic Exchange =========================================

  @Bean
  public Queue jsonQueue() {
    return new Queue(systemConfig.getJsonQueue());
  }

  @Bean
  public Queue jsonEmailQueue() {
    return new Queue(systemConfig.getJsonEmailQueue());
  }

  @Bean
  public TopicExchange topicExchange() {
    return new TopicExchange(systemConfig.getJsonExchange());
  }

  @Bean
  public Binding jsonBinding() {
    return BindingBuilder.bind(jsonQueue())
        .to(topicExchange())
        .with(systemConfig.getJsonRoutingkey());
  }

  @Bean
  public Binding jsonEmailBinding() {
    return BindingBuilder.bind(jsonEmailQueue())
        .to(topicExchange())
        .with(systemConfig.getJsonEmailRoutingkey());
  }
}
