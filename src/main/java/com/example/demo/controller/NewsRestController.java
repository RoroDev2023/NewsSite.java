package com.example.demo.controller;

import com.example.demo.annotation.CustomAnnotation;
import com.example.demo.entity.News;
import com.example.demo.service.NewsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * REST controller for managing news.
 */
@Tag(name = "News")
@RestController
public class NewsRestController {

    /**
     * Service to manage news.
     */
    private final NewsService newsService;

    /**
     * Constructor for NewsRestController.
     *
     * @param theNewsService the news service
     */
    @Autowired
    public NewsRestController(final NewsService theNewsService) {
        this.newsService = theNewsService;
    }

    /**
     * Get all news.
     *
     * @return list of all news
     */
    @Operation(
            description = "Get an endpoint for all news",
            summary = "Get the JSON format of all the news"
    )
    @CustomAnnotation
    @GetMapping("/news")
    public List<News> getNews() {
        return newsService.findAll();
    }

    /**
     * Get news by ID.
     *
     * @param id the ID of the news
     * @return the news with the specified ID
     */
    @Operation(
            description = "Get an endpoint for a news with a unique ID",
            summary = "Get the JSON format for a news by "
                    +
                    "providing a unique ID that exists in the database"
    )
    @CustomAnnotation
    @GetMapping("/news/{id}")
    public News getNewsById(@PathVariable final Long id) {
        return newsService.findById(id);
    }

    /**
     * Get news by title.
     *
     * @param title the title of the news
     * @return the news with the specified title
     */
    @Operation(
            description = "Get an endpoint for a news/all "
                    +
                    "news with a specific name",
            summary = "Get the JSON format for a news/all news by "
                    +
                    "providing a specific title that exists in the database"
    )
    @CustomAnnotation
    @GetMapping("/news/title/{title}")
    public News getNewsByTitle(@PathVariable final String title) {
        return newsService.findByTitle(title);
    }

    /**
     * Delete news by ID.
     *
     * @param id the ID of the news to delete
     * @return confirmation message
     */
    @Operation(
            description = "Delete the news with specific ID",
            summary = "Delete the news from the database by "
                    +
                    "providing a specific ID that exists in the database"
    )
    @CustomAnnotation
    @DeleteMapping("/news/delete/{id}")
    public String deleteNewsById(@PathVariable final Long id) {
        News tempNews = newsService.findById(id);
        newsService.delete(id);
        return "News deleted - " + tempNews;
    }

    /**
     * Create new news.
     *
     * @param newsRequest the request body containing news details
     * @return response entity with creation status
     */
    @Operation(
            description = "Post a new news by providing needed details",
            summary = "Post a new news by providing the title."
    )
    @CustomAnnotation
    @PostMapping("/news/create")
    public ResponseEntity<String> createNews(
            @RequestBody final News newsRequest) {
        News news = new News(newsRequest.getTitle());
        newsService.save(news);
        return ResponseEntity.status(
                HttpStatus.CREATED).body("News created - " + news);
    }
}

