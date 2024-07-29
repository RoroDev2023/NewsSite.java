/*package com.example.demo.userdao;

import com.example.demo.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public class userDAOImpl implements userDAO {

    private final EntityManager entityManager;

    @Autowired
    public userDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void save(User theuser) {
        entityManager.persist(theuser);
    }

    @Override
    public User findById(Integer id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public List<User> findAll() {
        TypedQuery<User> theQuery = entityManager.createQuery("FROM User order by lastName", User.class);
        return theQuery.getResultList();
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        User user = entityManager.find(User.class, id);
        entityManager.remove(user);
    }

    @Override
    public User findByName(String name) {
        TypedQuery<User> theQuery= entityManager.createQuery("FROM User where firstName=:theData", User.class);
        return theQuery.setParameter("theData", name).getSingleResult();

    }

    @Override
    @Transactional
    public void update(User user) {
        entityManager.merge(user);
    }
}*/
