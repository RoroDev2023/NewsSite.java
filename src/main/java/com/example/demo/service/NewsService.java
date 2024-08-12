package com.example.demo.service;

import com.example.demo.entity.News;
import java.util.List;

/**
 * Service interface for managing news articles.
 */
public interface NewsService {

    /**
     * Saves a news article.
     *
     * @param news the news article to save
     */
    void save(News news);

    /**
     * Finds a news article by ID.
     *
     * @param id the ID of the news article to find
     * @return the found news article
     */
    News findById(Long id);

    /**
     * Finds all news articles.
     *
     * @return a list of all news articles
     */
    List<News> findAll();

    /**
     * Deletes a news article by ID.
     *
     * @param id the ID of the news article to delete
     */
    void delete(Long id);

    /**
     * Finds a news article by title.
     *
     * @param title the title of the news article to find
     * @return the found news article
     */
    News findByTitle(String title);

    /**
     * Updates a news article.
     *
     * @param news the news article to update
     */
    void update(News news);
}

