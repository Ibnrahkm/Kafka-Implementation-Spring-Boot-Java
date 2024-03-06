package com.ibrahim.implementation.kafka.controller;

import com.google.gson.Gson;
import com.ibrahim.implementation.kafka.entites.User;
import com.ibrahim.implementation.kafka.response.Response;
import com.ibrahim.implementation.kafka.service.QueueService;
import com.ibrahim.implementation.kafka.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class APIController {

    @Autowired
    UserService service;

    @Autowired
    QueueService queueService;

    @PostMapping("/addUser")
    public Response addUser(@RequestBody String data) {
        return service.addUser(new Gson().fromJson(data, User.class));
    }

    @PostMapping("/updateUser")
    public Response updateUser(@RequestBody String data) {
        return service.updateUser(new Gson().fromJson(data, User.class));
    }

    @GetMapping("/getUser")
    public Response getUser(@RequestParam("id") String id) {
        return service.getUser(id);
    }


    @PostMapping("/deleteUser")
    public Response deleteUser(@RequestParam("id") String id) {
        return service.deleteUser(id);
    }

    @PostMapping("/getUsersByFilter")
    public Response getUsersByFilter(@RequestParam("balance") String balance) {
        return service.getUserListByFilter(balance);
    }

    @GetMapping("/getUsers")
    public Response getUsers() {
        return service.getUserList();
    }

    @GetMapping("/createAQueue")
    public String createAQueue(@RequestParam("queueName") String queueName) {
        return queueService.createQueue(queueName);
    }
}
