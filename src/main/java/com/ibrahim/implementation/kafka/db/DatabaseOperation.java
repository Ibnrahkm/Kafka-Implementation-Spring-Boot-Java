package com.ibrahim.implementation.kafka.db;

import com.ibrahim.implementation.kafka.entites.User;
import com.ibrahim.implementation.kafka.redis.CacheNames;
import com.ibrahim.implementation.kafka.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class DatabaseOperation {

    @Autowired
    UserRepository repository;


    @Caching(evict = {
            @CacheEvict(value = CacheNames.USER_LIST, key = "'user_list'", beforeInvocation = false, cacheManager = "cacheManager")
    }, put = {
            @CachePut(value = CacheNames.USER_LIST, key = "'user'.concat('_').concat(#user.username)", cacheManager = "cacheManager")
    }
    )
    public User addUser(User user) {
        try {

            return repository.save(user);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Cacheable(value = CacheNames.USER_LIST, key = "'user_'.concat(#id)", cacheManager = "cacheManager")
    public User getUser(Long id) {
        try {
            return repository.findById(id).get();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Caching(evict = {
            @CacheEvict(value = CacheNames.USER_LIST, key = "'user_list'", beforeInvocation = false, cacheManager = "cacheManager")
    }, put = {
            @CachePut(value = CacheNames.USER_LIST, key = "'user_'.concat(#user.id)", cacheManager = "cacheManager")
    }
    )
    public User updateUser(User user) {
        try {
            if (user.getId() != null)
                return repository.save(user);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Caching(evict = {
            @CacheEvict(value = CacheNames.USER_LIST, beforeInvocation = false, key = "'user_list'", cacheManager = "cacheManager"),
            @CacheEvict(value = CacheNames.USER_LIST, beforeInvocation = false, key = "'user_'.concat(#id)", cacheManager = "cacheManager")}
    )
    public boolean deleteUser(String id) {
        try {
            repository.deleteById(Long.parseLong(id));
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Cacheable(value = CacheNames.USER_LIST, key = "'user_list'", cacheManager = "cacheManager")
    public ArrayList<User> getAllUsers() {
        try {

            System.out.println(repository.findAll());
            return (ArrayList<User>) repository.findAll();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * we can use unless keyword also instead of condition
     * use case is simple suppose from client side ask to get data greater than or equal 20
     * db fetched data according to that but won't cache
     * if from client side ask for greater than or equal to 300 then it would cache
     * hence we can use it here
     *
     * @param balance
     * @return
     */
    @Cacheable(value = CacheNames.USER_LIST, key = "'user_list_filtered'", condition = "#balance>=300", cacheManager = "cacheManager")

    public ArrayList<User> getAllUsersFilterCache(Long balance) {
        try {
            return (ArrayList<User>) repository.getUserByBalance(balance);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
