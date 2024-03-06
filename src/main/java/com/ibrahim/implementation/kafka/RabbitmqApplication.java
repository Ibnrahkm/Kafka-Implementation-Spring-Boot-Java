package com.ibrahim.implementation.kafka;

import com.ibrahim.implementation.kafka.entites.User;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RabbitmqApplication {

    public static void main(String[] args) {
        SpringApplication.run(RabbitmqApplication.class, args);
    }


    @RabbitListener(queues = "defaultQueue")
    public void receiveMessageFromRabbitMQ(User user) {
        System.out.println("received: " + user);
    }
}
