# Spring Boot + Spring AMQP
- This project is a basic implementation of a Spring Boot application that consumes messages from a RabbitMQ message queue.
Sample RabbitMQ producer and consumer application with minimum setup.

Project contains two submodules:

- **producer** - with an example how to send message to RabbitMQ.
- **consumer** - with an example how to retrieve message from RabbitMQ.

## Prerequisites
- Java Development Kit (JDK) 8 or later
- Docker

# Run the following command to build and run the application:
- mvn clean install -DskipTests

- mvn spring-boot:run

# Start a RabbitMQ instance using Docker:

- docker run -d -it -p 15672:15672 -p 5672:5672 --name rabbitmq-local -e RABBITMQ_DEFAULT_USER=admin -e RABBITMQ_DEFAULT_PASS=admin rabbitmq:3-management

## Configuration

The Spring Boot application can be configured by modifying the application.yml file in the src/main/resources directory.

- spring.application.name: The name of the Spring Boot application.
- spring.rabbitmq.host: The hostname of the RabbitMQ server.
- spring.rabbitmq.port: The port number of the RabbitMQ server.
- spring.rabbitmq.username: The username used to authenticate with the RabbitMQ server.
- spring.rabbitmq.password: The password used to authenticate with the RabbitMQ server.

## Usage

Once the application is running, it will automatically consume messages from the configured RabbitMQ server. The application will listen for messages on the sample.queue queue.
