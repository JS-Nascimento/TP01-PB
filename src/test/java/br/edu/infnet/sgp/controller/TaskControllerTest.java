package br.edu.infnet.sgp.controller;

import br.edu.infnet.sgp.domain.entity.Task;
import br.edu.infnet.sgp.service.TaskService;
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

class TaskControllerTest {

    @Mock
    private TaskService taskService;

    @InjectMocks
    private TaskController taskController;

    private MockMvc mockMvc;
    private Task task;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(taskController).build();
        task = new Task();
        task.setId(1L);
        task.setName("Test Task");
        task.setStatus("Pending");
    }

    @Test
    @DisplayName("Get all tasks")
    void getAllTasks() throws Exception {
        List<Task> tasks = Arrays.asList(task);
        when(taskService.getAllTasks()).thenReturn(tasks);

        mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Test Task"));
    }

    @Test
    @DisplayName("Create a new task")
    void createTask() throws Exception {
        when(taskService.createTask(any(Task.class))).thenReturn(task);

        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Test Task\",\"status\":\"Pending\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Task"));
    }

    @Test
    @DisplayName("Update an existing task")
    void updateTask() throws Exception {
        when(taskService.updateTask(anyLong(), any(Task.class))).thenReturn(task);

        mockMvc.perform(put("/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Updated Task\",\"status\":\"Completed\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Task"));
    }


    @Test
    @DisplayName("Delete a task by ID")
    void deleteTask() throws Exception {
        doNothing().when(taskService).deleteTask(anyLong());

        mockMvc.perform(delete("/tasks/1"))
                .andExpect(status().isOk());

        verify(taskService, times(1)).deleteTask(1L);
    }
}