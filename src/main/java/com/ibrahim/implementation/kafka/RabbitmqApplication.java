package com.ibrahim.implementation.kafka;

import com.google.gson.Gson;
import com.ibrahim.implementation.kafka.entites.User;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;

@SpringBootApplication
public class RabbitmqApplication {


    public static void main(String[] args) {
        SpringApplication.run(RabbitmqApplication.class, args);
    }


    @RabbitListener(queues = "#{'${spring.rabbitmq.defaultQueue}'}")
    public void receiveMessageFromRabbitMQ(User user) {
        System.out.println("received_from_rabbit: " + new Gson().toJson(user));
    }


    /**
     * we may declare topics = "user-topic" this way also into the annotation @KafkaListener
     *
     * @param user
     */
    @KafkaListener(groupId = "#{'${spring.kafka.consumer.group-id}'}", topicPartitions = @TopicPartition(topic = "#{'${spring.kafka.user.topic}'}", partitions = {"#{'${spring.kafka.user.topic.partitions}'.split(',')}"}
    ), concurrency = "100")

    public void receiveMessageFromKafka(String user) {
        System.out.println("received_from_kafka: " + user);
    }
}
