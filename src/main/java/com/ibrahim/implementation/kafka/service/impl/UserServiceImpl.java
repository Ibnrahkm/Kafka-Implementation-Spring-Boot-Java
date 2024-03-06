package com.ibrahim.implementation.kafka.service.impl;

import com.google.gson.Gson;
import com.ibrahim.implementation.kafka.db.DatabaseOperation;
import com.ibrahim.implementation.kafka.entites.User;
import com.ibrahim.implementation.kafka.response.Response;
import com.ibrahim.implementation.kafka.service.UserService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("UserService")
public class UserServiceImpl implements UserService {
    @Autowired
    DatabaseOperation databaseOperation;

    @Autowired
    RabbitTemplate rabbitMessagingTemplate;

    @Autowired
    Queue queue;

    @Override
    public Response addUser(User user) {
        System.out.println("entered into addUser function");
        Response response = new Response(false, "failed to add user", "");
        try {
            user = databaseOperation.addUser(user);
            if (user.getId() > 0) {
                rabbitMessagingTemplate.convertAndSend(queue.getActualName(), queue.getActualName(), user);
                response.setMessage("successful");
                response.setData(user);
                response.setStatus(true);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return response;
    }

    @Override
    public Response getUser(String id) {
        System.out.println("entered into getUser function");
        Response response = new Response(false, "failed to get user", "");
        try {
            User user = databaseOperation.getUser(Long.parseLong(id));
            if (user.getId() > 0) {
                response.setMessage("successful");
                response.setData(user);
                response.setStatus(true);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return response;
    }

    @Override
    public Response getUserList() {
        System.out.println("entered into getUserList function");
        Response response = new Response(false, "failed to fetch users", "");
        try {
            response.setMessage("successful");
            response.setData(databaseOperation.getAllUsers());
            response.setStatus(true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return response;
    }

    @Override
    public Response updateUser(User user) {
        System.out.println("entered into updateUser function");
        Response response = new Response(false, "failed to update user", "");
        try {
            user = databaseOperation.updateUser(user);
            if (user.getId() > 0) {
                Message message = new Message(new Gson().toJson(user).getBytes("UTF-8"));
                rabbitMessagingTemplate.send(queue.getActualName(), queue.getActualName(), message);
                response.setMessage("successful");
                response.setData(user);
                response.setStatus(true);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return response;
    }

    @Override
    public Response deleteUser(String id) {
        System.out.println("entered into deleteUser function");
        Response response = new Response(false, "failed to delete user", "");
        try {
            boolean f = databaseOperation.deleteUser(id);
            if (f) {
                response.setMessage("successful");
                response.setData(id);
                response.setStatus(true);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return response;
    }

    @Override
    public Response getUserListByFilter(String balance) {
        System.out.println("entered into getUserList function");
        Response response = new Response(false, "failed to fetch users", "");
        try {
            response.setMessage("successful");
            response.setData(databaseOperation.getAllUsersFilterCache(Long.parseLong(balance)));
            response.setStatus(true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return response;
    }
}
