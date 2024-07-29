package com.example.demo.userservice;

import com.example.demo.entity.User;
import com.example.demo.userdao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public User findById(Long id) {

        Optional<User> result = userRepository.findById(id);
        User theUser = null;

        if (result.isPresent()) {
            theUser = result.get();
        }
        else {
            // we didn't find the user
            throw new RuntimeException("Did not find user id - " + id);
        }

        return theUser;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User findByUsername(String username) {
        Optional<User> result = Optional.ofNullable(userRepository.findByUsername(username));
        User theUser = null;

        if (result.isPresent()) {
            theUser = result.get();
        }
        else {
            throw new RuntimeException("Did not find user name - " + username);
        }
        return theUser;
    }

    @Override
    public void update(User user) {
        userRepository.save(user);
    }

}
