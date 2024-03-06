package com.ibrahim.implementation.kafka.service;


import com.ibrahim.implementation.kafka.entites.User;
import com.ibrahim.implementation.kafka.response.Response;

public interface UserService {

    public Response addUser(User user);

    public Response getUser(String id);

    public Response updateUser(User user);

    public Response deleteUser(String id);

    public Response getUserList();

    public Response getUserListByFilter(String balance);
}
