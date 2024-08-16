package br.edu.infnet.sgp.service;

import br.edu.infnet.sgp.domain.entity.User;
import br.edu.infnet.sgp.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setId(1L);
        user.setUsername("TestUser");
        user.setEmail("testuser@example.com");
    }

    @Test
    @DisplayName("Get all users")
    void getAllUsers() {
        when(userRepository.findAll()).thenReturn(Arrays.asList(user));
        List<User> users = userService.getAllUsers();
        assertThat(users).hasSize(1);
        assertThat(users.get(0).getUsername()).isEqualTo("TestUser");
    }

    @Test
    @DisplayName("Create a new user")
    void createUser() {
        when(userRepository.save(any(User.class))).thenReturn(user);
        User createdUser = userService.createUser(user);
        assertThat(createdUser.getId()).isEqualTo(1L);
        assertThat(createdUser.getUsername()).isEqualTo("TestUser");
    }

    @Test
    @DisplayName("Update an existing user")
    void updateUser() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);
        User updatedUser = userService.updateUser(1L, user);
        assertThat(updatedUser.getUsername()).isEqualTo("TestUser");
        assertThat(updatedUser.getEmail()).isEqualTo("testuser@example.com");
    }

    @Test
    @DisplayName("Update a non-existent user")
    void updateNonExistentUser() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
        try {
            userService.updateUser(1L, user);
        } catch (RuntimeException e) {
            assertThat(e.getMessage()).isEqualTo("Usuário não encontrado");
        }
    }

    @Test
    @DisplayName("Delete a user by ID")
    void deleteUser() {
        doNothing().when(userRepository).deleteById(anyLong());
        userService.deleteUser(1L);
        verify(userRepository, times(1)).deleteById(1L);
    }
}