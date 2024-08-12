package com.example.demo;

import com.example.demo.controller.NewsRestController;
import com.example.demo.entity.News;
import com.example.demo.service.NewsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.any;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

/**
 * Test class for NewsRestController.
 */
public final class NewsRestControllerTests {

    /**
     * Mocked NewsService.
     */
    @Mock
    private NewsService newsService;

    /**
     * Injected NewsRestController.
     */
    @InjectMocks
    private NewsRestController newsRestController;

    /**
     * MockMvc for testing controllers.
     */
    private MockMvc mockMvc;

    /**
     * Sets up the test environment before each test.
     */
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(newsRestController).build();
    }

    /**
     * Tests the getAllNews method.
     *
     * @throws Exception if an error occurs during the request
     */
    @Test
    public void testGetAllNews() throws Exception {
        News news1 = new News("Title1");
        news1.setId(1L);
        News news2 = new News("Title2");
        news2.setId(2L);
        List<News> newsList = Arrays.asList(news1, news2);

        when(newsService.findAll()).thenReturn(newsList);

        mockMvc.perform(get("/news")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(newsList.size()))
                .andExpect(jsonPath("$[0].id").value(news1.getId()))
                .andExpect(jsonPath("$[0].title").value(news1.getTitle()))
                .andExpect(jsonPath("$[1].id").value(news2.getId()))
                .andExpect(jsonPath("$[1].title").value(news2.getTitle()));

        verify(newsService, times(1)).findAll();
        verifyNoMoreInteractions(newsService);
    }

    /**
     * Tests the getNewsById method.
     *
     * @throws Exception if an error occurs during the request
     */

    @Test
    public void testGetNewsById() throws Exception {
        News news = new News("Title1");
        news.setId(1L);

        when(newsService.findById(1L)).thenReturn(news);

        mockMvc.perform(get("/news/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(news.getId()))
                .andExpect(jsonPath("$.title").value(news.getTitle()));

        verify(newsService, times(1)).findById(1L);
        verifyNoMoreInteractions(newsService);
    }

    /**
     * Tests the getNewsById method when the news is not found.
     *
     * @throws Exception if an error occurs during the request
     */


    @Test
    public void testGetNewsByIdNotFound() throws Exception {
        when(newsService.findById(1L)).thenThrow(new ResponseStatusException(
                HttpStatus.NOT_FOUND, "News not found with id: 1"));

        mockMvc.perform(get("/news/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(newsService, times(1)).findById(1L);
        verifyNoMoreInteractions(newsService);
    }

    /**
     * Tests the getNewsByTitle method.
     *
     * @throws Exception if an error occurs during the request
     */
    @Test
    public void testGetNewsByTitle() throws Exception {
        News news = new News("Title1");
        news.setId(1L);

        when(newsService.findByTitle("Title1")).thenReturn(news);

        mockMvc.perform(get("/news/title/{title}", "Title1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(news.getId()))
                .andExpect(jsonPath("$.title").value(news.getTitle()));

        verify(newsService, times(1)).findByTitle("Title1");
        verifyNoMoreInteractions(newsService);
    }

    /**
     * Tests the getNewsByTitle method when the news is not found.
     *
     * @throws Exception if an error occurs during the request
     */
    @Test
    public void testGetNewsByTitleNotFound() throws Exception {
        when(newsService.findByTitle("NonexistentTitle")).
                thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));

        mockMvc.perform(get("/news/title/{title}", "NonexistentTitle")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(newsService, times(1)).findByTitle("NonexistentTitle");
        verifyNoMoreInteractions(newsService);
    }

    /**
     * Tests the deleteNewsById method.
     *
     * @throws Exception if an error occurs during the request
     */
    @Test
    public void testDeleteNewsById() throws Exception {
        News news = new News("Title1");
        news.setId(1L);

        when(newsService.findById(1L)).thenReturn(news);
        doNothing().when(newsService).delete(1L);

        mockMvc.perform(delete("/news/delete/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("News deleted - " + news));

        verify(newsService, times(1)).findById(1L);
        verify(newsService, times(1)).delete(1L);
        verifyNoMoreInteractions(newsService);
    }

    /**
     * Tests the deleteNewsById method when the news is not found.
     *
     * @throws Exception if an error occurs during the request
     */
    @Test
    public void testDeleteNewsByIdNotFound() throws Exception {
        when(newsService.findById(1L)).
                thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));

        mockMvc.perform(delete("/news/delete/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(newsService, times(1)).findById(1L);
        verifyNoMoreInteractions(newsService);
    }

    /**
     * Tests the createNews method.
     *
     * @throws Exception if an error occurs during the request
     */
    @Test
    public void testCreateNews() throws Exception {
        News news = new News("Title123");

        mockMvc.perform(post("/news/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Title123\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().string("News created - " + news));

        verify(newsService, times(1)).save(any(News.class));
        verifyNoMoreInteractions(newsService);
    }

}

