package co.test.springbootrabbitmq.producer;

import co.test.springbootrabbitmq.config.SystemConfig;
import co.test.springbootrabbitmq.dto.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageProducer {

  private final SystemConfig systemConfig;
  private final AmqpTemplate amqpTemplate;

  public void messageSending(String message) {
    try {
      log.info("Message sending for text exchange -> {}", message);

      amqpTemplate.convertAndSend(
          systemConfig.getExchange(), systemConfig.getRoutingkey(), message);
    } catch (AmqpException e) {
      log.error("Error occurred while sending message: {}", e.getMessage());
      // You can add additional error handling logic here,
      // such as retrying the operation or notifying the appropriate parties
    }
  }

  public void jsonObjectSending(User user) {
    try {
      log.info("Message sending for json exchange -> {}", user.toString());

      // For Json queue
      amqpTemplate.convertAndSend(
          systemConfig.getJsonExchange(), systemConfig.getJsonRoutingkey(), user);

      // For Json queue as well as email queue
      amqpTemplate.convertAndSend(
          systemConfig.getJsonExchange(), systemConfig.getJsonEmailRoutingkey(), user);

      log.info("Message sent -> {}", user.toString());
    } catch (AmqpException e) {
      log.error("Error occurred while sending message: {}", e.getMessage(), e);
      // You can add additional error handling logic here,
      // such as retrying the operation or notifying the appropriate parties
    }
  }
}
