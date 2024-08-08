package com.example.demo.entity;

import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import lombok.NoArgsConstructor;

/**
 * Entity class representing News.
 */
@Entity
@Table(name = "news")
@NoArgsConstructor
@Data
public class News {

    /**
     * The unique identifier for news.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The title of the news.
     */
    @Column(name = "title")
    private String title;

    /**
     * Constructs a new News with the specified title.
     *
     * @param newsTitle the title of the news
     */
    public News(final String newsTitle) {
        this.title = newsTitle;
    }

    /**
     * Returns a string representation of the News.
     *
     * @return a string representation of the News
     */
    @Override
    public String toString() {
        return "News{"
                +
                "id=" + id
                +
                ", title='" + title + '\''
                +
                '}';
    }
}
