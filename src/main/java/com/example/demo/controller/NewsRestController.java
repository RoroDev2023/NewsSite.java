package com.example.demo.controller;

import com.example.demo.entity.News;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.newsdao.newsDAO;

import java.util.List;

@Tag(name="News")
@RestController
public class NewsRestController {

    private final newsDAO newsDAO;

    @Autowired
    public NewsRestController(newsDAO theNewsDAO) {
        this.newsDAO = theNewsDAO;
    }

    @Operation(
            description = "Get an endpoint for all news",
            summary = "Get the JSON format of all the news"
    )
    @GetMapping("/news")
    public List<News> getNews() {
        return newsDAO.findAllNews();
    }

    @Operation(
            description = "Get an endpoint for a news with a unique ID",
            summary = "Get the JSON format for a news by providing a unique ID that exists in the database"
    )
    @GetMapping("/news/{id}")
    public News getNewsById(@PathVariable int id) {
        News news = newsDAO.findNewsById(id);
        if (news == null) {
            throw new RuntimeException("News not found");
        }
        return news;
    }

    @Operation(
            description = "Get an endpoint for a news/all news with a specific name",
            summary = "Get the JSON format for a news/all news by providing a specific title that exists in the database"
    )
    @GetMapping("/news/title/{title}")
    public News getNewsByTitle(@PathVariable String title) {
        News news = newsDAO.findNewsbyTitle(title);

        if (news == null) {
            throw new RuntimeException("News not found");
        }
        return news;
    }
}
