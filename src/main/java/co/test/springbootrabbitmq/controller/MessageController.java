package co.test.springbootrabbitmq.controller;

import co.test.springbootrabbitmq.dto.User;
import co.test.springbootrabbitmq.producer.MessageProducer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/api/message")
public class MessageController {

  private final MessageProducer messageProducer;

  /**
   * Instantiates a new message controller.
   *
   * @param messageProducer the message producer
   */
  public MessageController(MessageProducer messageProducer) {
    this.messageProducer = messageProducer;
  }

  /**
   * Produce message.
   *
   * @param message the message
   * @return the String response
   */
  @PostMapping("")
  public ResponseEntity<String> produceMessage(@RequestParam("message") String message) {
    messageProducer.messageSending(message);
    return ResponseEntity.ok("Message has been sent Successfully...");
  }

  /**
   * Produce json message.
   *
   * @param user info
   * @return the String response
   */
  @PostMapping("/json")
  public ResponseEntity<String> produceMessage(@RequestBody User user) {
    messageProducer.jsonObjectSending(user);
    return ResponseEntity.ok("User details are Submitted.");
  }

  /**
   * Test.
   *
   * @return the string
   */
  @GetMapping("/test")
  public String test() {
    return "Hello springboot-producer service running...";
  }

  /**
   * Info
   *
   * @return the string
   */
  @GetMapping("/info")
  public String info() {
    String os = System.getProperty("os.name");
    String version = System.getProperty("os.version");
    String architecture = System.getProperty("os.arch");
    String javaVersion = System.getProperty("java.version");
    String user = System.getProperty("user.name");

    return "OS: "
        + os
        + ",  Version: "
        + version
        + ",  Architechture: "
        + architecture
        + ",  Java Version: "
        + javaVersion
        + ",  User: "
        + user;
  }
}
