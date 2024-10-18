mvn clean compile package -DskipTests && \
docker-compose down && \
docker build -t  personal/springboot-producer . && \
docker-compose up -d && \
docker logs -f springboot-rabbitmq-producer
