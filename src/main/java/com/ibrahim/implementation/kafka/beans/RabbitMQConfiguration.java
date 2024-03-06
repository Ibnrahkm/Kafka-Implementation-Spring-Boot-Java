package com.ibrahim.implementation.kafka.beans;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {
    @Value("${spring.rabbitmq.host}")
    String host;
    @Value("${spring.rabbitmq.port}")
    int port;
    @Value("${spring.rabbitmq.username}")
    String username;
    @Value("${spring.rabbitmq.password}")
    String password;

    @Value("${spring.rabbitmq.defaultQueue}")
    String defaultQueue;


    @Bean
    public CachingConnectionFactory connectionFactoryRabbitMq() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setConnectionTimeout(10000);
        connectionFactory.setHost(host);
        connectionFactory.setPort(port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        return connectionFactory;
    }

    @Bean
    public Queue myQueue() {
        return new Queue(defaultQueue, false);
    }

    @Bean
    public DirectExchange exchange() {
        //could be topicExchange,fanoutExchange,headerExchange
        return new DirectExchange(defaultQueue);
    }
    @Bean
    public Binding binding() {
        return BindingBuilder.bind(myQueue()).to(exchange()).with(defaultQueue);
    }

    @Bean
    public Jackson2JsonMessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setConnectionFactory(connectionFactoryRabbitMq());
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }


}
