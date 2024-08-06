package com.example.demo.controller;

import com.example.demo.annotation.CustomAnnotation;
import com.example.demo.entity.News;
import com.example.demo.service.NewsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name="News")
@RestController
public class NewsRestController {

    private final NewsService newsService;

    @Autowired
    public NewsRestController(NewsService theNewsService) {
        newsService = theNewsService;
    }

    @Operation(
            description = "Get an endpoint for all news",
            summary = "Get the JSON format of all the news"
    )
    @CustomAnnotation
    @GetMapping("/news")
    public List<News> getNews() {
        return newsService.findAll();
    }


    @Operation(
            description = "Get an endpoint for a news with a unique ID",
            summary = "Get the JSON format for a news by providing a unique ID that exists in the database"
    )
    @CustomAnnotation
    @GetMapping("/news/{id}")
    public News getNewsById(@PathVariable Long id) {
        return newsService.findById(id);
    }


    @Operation(
            description = "Get an endpoint for a news/all news with a specific name",
            summary = "Get the JSON format for a news/all news by providing a specific title that exists in the database"
    )
    @CustomAnnotation
    @GetMapping("/news/title/{title}")
    public News getNewsByTitle(@PathVariable String title) {
        return newsService.findByTitle(title);
    }


    @Operation(
            description = "Delete the news with specific ID",
            summary = "Delete the news from the database by providing a specific ID that exists in the database"
    )
    @CustomAnnotation
    @DeleteMapping("/news/delete/{id}")
    public String deleteNewsById(@PathVariable Long id) {
        News tempNews = newsService.findById(id);
        newsService.delete(id);

        return "News deleted - " + tempNews;
    }


    @Operation(
            description = "Post a new news by providing needed details",
            summary="Post a new news by providing the title."
    )
    @CustomAnnotation
    @PostMapping("/news/create")
    public ResponseEntity<String> createNews(@RequestBody News newsRequest) {
        News news = new News(newsRequest.getTitle());
        newsService.save(news);
        return ResponseEntity.status(HttpStatus.CREATED).body("News created - " + news);
    }
}
