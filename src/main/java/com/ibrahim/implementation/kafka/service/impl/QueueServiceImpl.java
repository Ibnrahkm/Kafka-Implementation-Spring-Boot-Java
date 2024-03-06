package com.ibrahim.implementation.kafka.service.impl;

import com.ibrahim.implementation.kafka.service.QueueService;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("QueueService")
public class QueueServiceImpl implements QueueService {

    @Autowired
    CachingConnectionFactory connectionFactory;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Override
    public String createQueue(String name) {
        try {
            // full custom queue preparation
            Connection connection = connectionFactory.createConnection();
            Channel channel = connection.createChannel(true);
            AMQP.Queue.DeclareOk declareOk = channel.queueDeclare(name, true, false, false, null);//queue name, durable,exclusive,auto delete
            AMQP.Exchange.DeclareOk declareOk1 = channel.exchangeDeclare(name, "direct");// exchange name,type
            AMQP.Exchange.BindOk ok = channel.exchangeBind(name, name, name); //destination,source,routing key
            AMQP.Queue.BindOk ok1 = channel.queueBind(name, name, name);//queuename,exchangename,routing key
            channel.close();
            connection.close();
            return declareOk.getQueue();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "failed to create queue";
    }
}
