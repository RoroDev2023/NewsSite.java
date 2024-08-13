package com.example.demo;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserServiceImpl;
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



/**
 * Unit tests for the {@link UserServiceImpl} class.
 */
public class UserServiceImplTests {
    /**
     * Mocked instance of {@link UserRepository}.
     */
    @Mock
    private UserRepository userRepository;
    /**
     * Injects the mocks into {@link UserServiceImpl}.
     */
    @InjectMocks
    private UserServiceImpl userServiceImpl;

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
        User user = new User("john", "wpwv", 1);
        user.setId(1L);

        // Call the save method on the service
        userServiceImpl.save(user);

        // Verify that the save method was called on the repository
        verify(userRepository, times(1)).save(user);
    }
    /**
     * Tests the method for finding a user by ID.
     */
    @Test
    public void testFindById() {
        User user = new User("john", "wpwv", 1);
        user.setId(1L);


        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User foundUser = userServiceImpl.findById(1L);

        assertEquals(user, foundUser);
        verify(userRepository, times(1)).findById(1L);
    }
    /**
     * Tests the method for not finding a user by ID.
     */
    @Test
    public void testFindByIdNotFound() throws Exception {
        when(userRepository.findById(1L)).thenReturn(empty());

        assertThrows(ResponseStatusException.class,
                () -> userServiceImpl.findById(1L));
        verify(userRepository, times(1)).findById(1L);
    }

    /**
     * Tests the method for finding all users.
     */
    @Test
    public void testFindAll() {
        List<User> mockUserList = Arrays.asList(
                new User("john", "wpwv", 1),
                new User("mary", "veve", 1)
        );

        when(userRepository.findAll()).thenReturn(mockUserList);

        List<User> result = userServiceImpl.findAll();

        assertEquals(2, result.size());
        assertEquals("john", result.get(0).getUsername());
        assertEquals("mary", result.get(1).getUsername());
        verify(userRepository, times(1)).findAll();
    }
    /**
     * Tests the method for deleting a user by ID.
     */
    @Test
    public void testDelete() {
        User user = new User("john", "wpwv", 1);
        user.setId(1L);

        // Directly verify the deleteById method call
        userServiceImpl.save(user);
        userServiceImpl.delete(2L);

        // Verify that the deleteById method was called on the repository
        verify(userRepository, times(1)).deleteById(2L);
    }
    /**
     * Tests the method for not deleting a user by ID.
     */
    @Test
    public void testDeleteNotFound() {
        doThrow(new IllegalArgumentException("User not found"))
                .when(userRepository).deleteById(1L);

        assertThrows(IllegalArgumentException.class,
                () -> userServiceImpl.delete(1L));
        verify(userRepository, times(1)).deleteById(1L);
    }
    /**
     * Tests the method for finding a user by username.
     */
    @Test
    public void testFindByUsername() {
        User user = new User("john", "wpwv", 1);
        user.setId(1L);

        when(userRepository.findByUsername("john")).thenReturn((user));

        User foundUser = userServiceImpl.findByUsername("john");

        assertEquals(user, foundUser);
        verify(userRepository, times(1)).findByUsername("john");
    }
    /**
     * Tests the method for not finding a user by username.
     */
    @Test
    public void testFindByUsernameNotFound() {
        when(userRepository.findByUsername("Gev")).thenReturn(null);
        assertThrows(ResponseStatusException.class,
                () -> userServiceImpl.findByUsername("Gev"));
        verify(userRepository, times(1)).findByUsername("Gev");
    }
    /**
     * Tests the method for updating a user.
     */
    @Test
    public void testUpdate() {
        User user = new User("john", "wpwv", 1);
        user.setId(1L);

        userServiceImpl.update(user);
        verify(userRepository, times(1)).save(user);
    }
}
