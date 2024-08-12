package com.example.demo.service;

import com.example.demo.entity.News;
import com.example.demo.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

/**
 * Service implementation for managing news entities.
 */
@Service
public class NewsServiceImpl implements NewsService {

    /**
     * The repository used for accessing news entities.
     */
    private final NewsRepository newsRepository;

    /**
     * Constructs a new NewsServiceImpl with the given NewsRepository.
     *
     * @param newsRepo the news repository, must not be null
     */
    @Autowired
    public NewsServiceImpl(final NewsRepository newsRepo) {
        this.newsRepository = newsRepo;
    }

    /**
     * Saves a news entity.
     *
     * @param news the news entity to save, must not be null
     */
    @Override
    public void save(final News news) {
        newsRepository.save(news);
    }

    /**
     * Finds a news entity by its ID.
     *
     * @param id the ID of the news entity to find, must not be null
     * @return the found news entity,
     * or throws a ResponseStatusException if not found
     */
    @Override
    public News findById(final Long id) {
        return newsRepository.findById(Math.toIntExact(id))
                .orElseThrow(()
                        -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "News not found with id: " + id));
    }

    /**
     * Finds all news entities.
     *
     * @return a list of all news entities
     */
    @Override
    public List<News> findAll() {
        return newsRepository.findAll();
    }

    /**
     * Deletes a news entity by its ID.
     *
     * @param id the ID of the news entity to delete, must not be null
     */
    @Override
    public void delete(final Long id) {
        newsRepository.deleteById(Math.toIntExact(id));
    }

    /*@Override
    public void delete(final Long id) {
        News news = newsRepository.findById(Math.toIntExact(id))
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "News not found with id - " + id));

        // Delete the news item
        newsRepository.deleteById(Math.toIntExact(id));
    }*/
    /**
     * Finds a news entity by its title.
     *
     * @param title the title of the
     *        news entity to find, must not be null
     * @return the found news entity,
     *        or throws a ResponseStatusException if not found
     */

    @Override
    public News findByTitle(final String title) {
        return Optional.ofNullable(newsRepository.findByTitle(title))
                .orElseThrow(()
                        -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "News not found with title - " + title));
    }


    /**
     * Updates a news entity.
     *
     * @param news the news entity to update, must not be null
     */
    @Override
    public void update(final News news) {
        newsRepository.save(news);
    }
}

