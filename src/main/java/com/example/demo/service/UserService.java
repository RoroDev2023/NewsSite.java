package com.example.demo.service;

import com.example.demo.entity.User;

import java.util.List;

/**
 * Service interface for managing users.
 */
public interface UserService {

    /**
     * Saves a user.
     *
     * @param user the user to save
     */
    void save(User user);

    /**
     * Finds a user by ID.
     *
     * @param id the ID of the user to find
     * @return the found user
     */
    User findById(Long id);

    /**
     * Finds all users.
     *
     * @return a list of all users
     */
    List<User> findAll();

    /**
     * Deletes a user by ID.
     *
     * @param id the ID of the user to delete
     */
    void delete(Long id);

    /**
     * Finds a user by username.
     *
     * @param username the username of the user to find
     * @return the found user
     */
    User findByUsername(String username);

    /**
     * Updates a user.
     *
     * @param user the user to update
     */
    void update(User user);
}
