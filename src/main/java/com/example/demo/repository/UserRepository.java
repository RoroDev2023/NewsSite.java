package com.example.demo.repository;

import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Find a news item by its title.
     *
     * @param firstName the name of the user
     * @return the user item with the given firstName
     */
    User findByUsername(String firstName);
}
