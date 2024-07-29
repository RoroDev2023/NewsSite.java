package com.example.demo.newsdao;
import com.example.demo.entity.News;
import java.util.List;

public interface newsDAO {
    void saveNews(News news);
    News findNewsById(int id);
    List<News> findAllNews();
    void deleteNewsById(int id);
    News findNewsbyTitle(String title);
    void updateNews(News news);

}
