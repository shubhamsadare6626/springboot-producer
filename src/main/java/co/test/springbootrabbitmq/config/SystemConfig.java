package co.test.springbootrabbitmq.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class SystemConfig {

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

  @Value("${rabbitmq.json.queue}")
  private String jsonQueue;

  @Value("${rabbitmq.json.email-queue}")
  private String jsonEmailQueue;

  @Value("${rabbitmq.json.exchange}")
  private String jsonExchange;

  @Value("${rabbitmq.json.routingkey}")
  private String jsonRoutingkey;

  @Value("${rabbitmq.json.email-routingkey}")
  private String jsonEmailRoutingkey;
}
