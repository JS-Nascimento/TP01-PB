package br.edu.infnet.sgp.controller;

import br.edu.infnet.sgp.domain.entity.User;
import br.edu.infnet.sgp.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;
    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        user = new User();
        user.setId(1L);
        user.setUsername("TestUser");
        user.setEmail("testuser@example.com");
    }

    @Test
    @DisplayName("Get all users")
    void getAllUsers() throws Exception {
        List<User> users = Arrays.asList(user);
        when(userService.getAllUsers()).thenReturn(users);

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].username").value("TestUser"));
    }

    @Test
    @DisplayName("Create a new user")
    void createUser() throws Exception {
        when(userService.createUser(any(User.class))).thenReturn(user);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"TestUser\",\"email\":\"testuser@example.com\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("TestUser"));
    }

    @Test
    @DisplayName("Update an existing user")
    void updateUser() throws Exception {
        when(userService.updateUser(anyLong(), any(User.class))).thenReturn(user);

        mockMvc.perform(put("/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"UpdatedUser\",\"email\":\"updateduser@example.com\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("TestUser"));
    }


    @Test
    @DisplayName("Delete a user by ID")
    void deleteUser() throws Exception {
        doNothing().when(userService).deleteUser(anyLong());

        mockMvc.perform(delete("/users/1"))
                .andExpect(status().isOk());

        verify(userService, times(1)).deleteUser(1L);
    }
}