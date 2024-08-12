package com.example.demo.repository;

import com.example.demo.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRepository extends JpaRepository<News, Integer> {

    /**
     * Find a news item by its title.
     *
     * @param title the title of the news item
     * @return the news item with the given title
     */
    News findByTitle(String title);
}
