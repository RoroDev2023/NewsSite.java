package com.example.demo.newsdao;

import com.example.demo.entity.News;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class newsDAOImpl implements newsDAO {

    private final EntityManager entityManager;

    @Autowired
    public newsDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    @Override
    @Transactional
    public void saveNews(News news) {
        entityManager.persist(news);
    }

    @Override
    public News findNewsById(int id) {
        return entityManager.find(News.class, id);
    }

    @Override
    public List<News> findAllNews() {
        TypedQuery<News> theQuery = entityManager.createQuery("FROM News", News.class);
        return theQuery.getResultList();
    }

    @Override
    @Transactional
    public void deleteNewsById(int id) {
        News news = entityManager.find(News.class, id);
        entityManager.remove(news);
    }

    @Override
    public News findNewsbyTitle(String title) {
        TypedQuery<News> theQuery= entityManager.createQuery("FROM News where title=:theData", News.class);
        return theQuery.setParameter("theData", title).getSingleResult();
    }

    @Override
    @Transactional
    public void updateNews(News news) {
        entityManager.merge(news);
    }
}

