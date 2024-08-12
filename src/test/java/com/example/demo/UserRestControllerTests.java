package com.example.demo;

import com.example.demo.controller.UserRestController;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

/**
 * Unit tests for the {@link UserRestController} class.
 */
public class UserRestControllerTests {

    /**
     * Mocked UserService.
     */
    @Mock
    private UserService userService;

    /**
     * Injected UserRestController with the mocked UserService.
     */
    @InjectMocks
    private UserRestController userRestController;

    /**
     * MockMvc instance for performing HTTP requests in tests.
     */
    private MockMvc mockMvc;

    /**
     * Set up method to initialize mocks and MockMvc.
     */
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userRestController).build();
    }

    /**
     * Test case for retrieving all users.
     * @throws Exception if any error occurs during the request.
     */
    @Test
    public void testGetAllUsers() throws Exception {
        User user1 = new User("john_doe", "password123", 1);
        user1.setId(1L);
        User user2 = new User("jane_doe", "password456", 1);
        user2.setId(2L);
        List<User> users = Arrays.asList(user1, user2);

        when(userService.findAll()).thenReturn(users);

        mockMvc.perform(get("/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(users.size()))
                .andExpect(jsonPath("$[0].id").value(user1.getId()))
                .andExpect(jsonPath("$[0].username").value(user1.getUsername()))
                .andExpect(jsonPath("$[0].password").value(user1.getPassword()))
                .andExpect(jsonPath("$[0].enabled").value(user1.getEnabled()))
                .andExpect(jsonPath("$[1].id").value(user2.getId()))
                .andExpect(jsonPath("$[1].username").value(user2.getUsername()))
                .andExpect(jsonPath("$[1].password").value(user2.getPassword()))
                .andExpect(jsonPath("$[1].enabled").value(user2.getEnabled()));

        verify(userService, times(1)).findAll();
        verifyNoMoreInteractions(userService);
    }

    /**
     * Test case for retrieving a user by their ID.
     * @throws Exception if any error occurs during the request.
     */
    @Test
    public void testGetUserById() throws Exception {
        User user = new User("john_doe", "password123", 1);
        user.setId(1L);

        when(userService.findById(1L)).thenReturn(user);

        mockMvc.perform(get("/users/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(user.getId()))
                .andExpect(jsonPath("$.username").value(user.getUsername()))
                .andExpect(jsonPath("$.password").value(user.getPassword()))
                .andExpect(jsonPath("$.enabled").value(user.getEnabled()));

        verify(userService, times(1)).findById(1L);
        verifyNoMoreInteractions(userService);
    }

    /**
     * Test case for retrieving a user by their ID when the user is not found.
     * @throws Exception if any error occurs during the request.
     */
    @Test
    public void testGetUserByIdNotFound() throws Exception {
        when(userService.findById(1L)).thenThrow(
                new ResponseStatusException(HttpStatus.NOT_FOUND));

        mockMvc.perform(get("/users/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(userService, times(1)).findById(1L);
        verifyNoMoreInteractions(userService);
    }

    /**
     * Test case for retrieving a user by their username.
     * @throws Exception if any error occurs during the request.
     */
    @Test
    public void testGetUserByUsername() throws Exception {
        User user = new User("john_doe", "password123", 1);
        user.setId(1L);

        when(userService.findByUsername("john_doe")).thenReturn(user);

        mockMvc.perform(get("/users/name/{username}", "john_doe")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(user.getId()))
                .andExpect(jsonPath("$.username").value(user.getUsername()))
                .andExpect(jsonPath("$.password").value(user.getPassword()))
                .andExpect(jsonPath("$.enabled").value(user.getEnabled()));

        verify(userService, times(1)).findByUsername("john_doe");
        verifyNoMoreInteractions(userService);
    }

    /**
     * Test case for retrieving a user by their
     *      username when the user is not found.
     * @throws Exception if any error occurs during the request.
     */
    @Test
    public void testGetUserByUsernameNotFound() throws Exception {
        when(userService.findByUsername("johnnny_doe")).
                thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));

        mockMvc.perform(get("/users/name/{username}", "johnnny_doe")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(userService, times(1)).findByUsername("johnnny_doe");
        verifyNoMoreInteractions(userService);
    }

    /**
     * Test case for deleting a user by their ID.
     * @throws Exception if any error occurs during the request.
     */
    @Test
    public void testDeleteUserById() throws Exception {
        User user = new User("john_doe", "password123", 1);
        user.setId(1L);

        when(userService.findById(1L)).thenReturn(user);
        doNothing().when(userService).delete(1L);

        mockMvc.perform(delete("/users/delete/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("User deleted - " + user));

        verify(userService, times(1)).findById(1L);
        verify(userService, times(1)).delete(1L);
        verifyNoMoreInteractions(userService);
    }

    /**
     * Test case for deleting a user by their ID when the user is not found.
     * @throws Exception if any error occurs during the request.
     */
    @Test
    public void testDeleteUserByIdNotFound() throws Exception {
        when(userService.findById(1L)).thenThrow(
                new ResponseStatusException(HttpStatus.NOT_FOUND));

        mockMvc.perform(delete("/users/delete/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(userService, times(1)).findById(1L);
        verifyNoMoreInteractions(userService);
    }
}



