package com.example.demo;

import com.example.demo.entity.News;
import com.example.demo.repository.NewsRepository;
import com.example.demo.service.NewsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.empty;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doThrow;

public class NewsServiceImplTests {

    /**
     * Mocked instance of {@link NewsRepository}.
     */
    @Mock
    private NewsRepository newsRepository;
    /**
     * Injects the mocks into {@link NewsServiceImpl}.
     */
    @InjectMocks
    private NewsServiceImpl newsServiceImpl;  // Directly test NewsServiceImpl
    /**
     * Sets up the test environment by initializing mocks.
     */
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    /**
     * Tests the method for saving a user.
     */
    @Test
    public void testSave() {
        News news = new News("Title1");
        news.setId(1L);

        // Call the save method on the service
        newsServiceImpl.save(news);

        // Verify that the save method was called on the repository
        verify(newsRepository, times(1)).save(news);
    }
    /**
     * Tests the method for finding news by ID.
     */
    @Test
    public void testFindById() {
        News news = new News("Title1");
        news.setId(1L);

        when(newsRepository.findById(1)).thenReturn(Optional.of(news));

        News foundNews = newsServiceImpl.findById(1L);

        assertEquals(news, foundNews);
        verify(newsRepository, times(1)).findById(1);
    }

    /**
     * Tests the method for not finding news by ID.
     */
    @Test
    public void testFindByIdNotFound() throws Exception {
        when(newsRepository.findById(1)).thenReturn(empty());

        assertThrows(ResponseStatusException.class,
                () -> newsServiceImpl.findById(1L));
        verify(newsRepository, times(1)).findById(1);
    }

    /**
     * Tests the method for finding all news.
     */
    @Test
    public void testFindAll() {
        List<News> mockNewsList = Arrays.asList(
                new News("Title 1"),
                new News("Title 2")
        );

        when(newsRepository.findAll()).thenReturn(mockNewsList);

        List<News> result = newsServiceImpl.findAll();

        assertEquals(2, result.size());
        assertEquals("Title 1", result.get(0).getTitle());
        assertEquals("Title 2", result.get(1).getTitle());
        verify(newsRepository, times(1)).findAll();
    }
    /**
     * Tests the method for deleting news by ID.
     */
    @Test
    public void testDelete() {
        News news = new News("Title to delete");
        news.setId(2L);

        // Directly verify the deleteById method call
        newsServiceImpl.save(news);
        newsServiceImpl.delete(2L);

        // Verify that the deleteById method was called on the repository
        verify(newsRepository, times(1)).deleteById(Math.toIntExact(2L));
    }
    /**
     * Tests the method for not deleting news by ID.
     */
    @Test
    public void testDeleteNotFound() {
        doThrow(new IllegalArgumentException("News not found"))
                .when(newsRepository).deleteById(Math.toIntExact(1L));

        assertThrows(IllegalArgumentException.class,
                () -> newsServiceImpl.delete(1L));
        verify(newsRepository, times(1)).deleteById(Math.toIntExact(1L));
    }
    /**
     * Tests the method for finding news by title.
     */
    @Test
    public void testFindByTitle() {
        News news = new News("Unique1");
        news.setId(1L);

        when(newsRepository.findByTitle("Unique1")).thenReturn((news));

        News foundNews = newsServiceImpl.findByTitle("Unique1");

        assertEquals(news, foundNews);
        verify(newsRepository, times(1)).findByTitle("Unique1");
    }
    /**
     * Tests the method for not finding news by title.
     */
    @Test
    public void testFindByTitleNotFound() {
        when(newsRepository.findByTitle("News")).thenReturn(null);
        assertThrows(ResponseStatusException.class,
                () -> newsServiceImpl.findByTitle("News"));
        verify(newsRepository, times(1)).findByTitle("News");
    }

    /**
     * Tests the method for updating news.
     */
    @Test
    public void testUpdate() {
        News news = new News("Unique1");
        news.setId(1L);

        newsServiceImpl.update(news);
        verify(newsRepository, times(1)).save(news);
    }

}
