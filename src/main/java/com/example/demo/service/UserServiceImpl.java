package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

/**
 * Info about this package doing something for package-info.java file.
 */

@Service
public class UserServiceImpl implements UserService {

    /**
     * User repository for accessing user data.
     */
    private final UserRepository userRepo;

    /**
     * Constructor for UserServiceImpl.
     * @param userRepository the user repository
     */
    @Autowired
    public UserServiceImpl(final UserRepository userRepository) {
        this.userRepo = userRepository;
    }

    /**
     * Save a user.
     * @param user the user to save
     */
    @Override
    public void save(final User user) {
        userRepo.save(user);
    }

    /**
     * Find a user by ID.
     * @param id the user ID
     * @return the user with the given ID
     */
    @Override
    public User findById(final Long id) {
        return userRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "User not found with id: " + id));
    }

    /**
     * Find all users.
     * @return list of all users
     */
    @Override
    public List<User> findAll() {
        return userRepo.findAll();
    }

    /**
     * Delete a user by ID.
     * @param id the user ID
     */
    @Override
    public void delete(final Long id) {
        userRepo.deleteById(id);
    }

    /**
     * Find a user by username.
     * @param username the username
     * @return the user with the given username
     */
    @Override
    public User findByUsername(final String username) {
        return Optional.ofNullable(userRepo.findByUsername(username))
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "User not found with username: " + username));
    }

    /**
     * Update a user.
     * @param user the user to update
     */
    @Override
    public void update(final User user) {
        userRepo.save(user);
    }
}

