package com.example.demo.userservice;

import com.example.demo.entity.User;

import java.util.List;

public interface UserService {
    void save(User user);
    User findById(Long id);
    List<User> findAll();
    void delete(Long id);
    User findByUsername(String username);
    void update(User user);
}
