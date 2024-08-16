package br.edu.infnet.sgp.service;

import br.edu.infnet.sgp.domain.entity.Task;
import br.edu.infnet.sgp.repository.TaskRepository;
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

class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    private Task task;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        task = new Task();
        task.setId(1L);
        task.setName("Test Task");
        task.setStatus("Pending");
    }

    @Test
    @DisplayName("Get all tasks")
    void getAllTasks() {
        when(taskRepository.findAll()).thenReturn(Arrays.asList(task));
        List<Task> tasks = taskService.getAllTasks();
        assertThat(tasks).hasSize(1);
        assertThat(tasks.get(0).getName()).isEqualTo("Test Task");
    }

    @Test
    @DisplayName("Create a new task")
    void createTask() {
        when(taskRepository.save(any(Task.class))).thenReturn(task);
        Task createdTask = taskService.createTask(task);
        assertThat(createdTask.getId()).isEqualTo(1L);
        assertThat(createdTask.getName()).isEqualTo("Test Task");
    }

    @Test
    @DisplayName("Update an existing task")
    void updateTask() {
        when(taskRepository.findById(anyLong())).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenReturn(task);
        Task updatedTask = taskService.updateTask(1L, task);
        assertThat(updatedTask.getName()).isEqualTo("Test Task");
        assertThat(updatedTask.getStatus()).isEqualTo("Pending");
    }

    @Test
    @DisplayName("Update a non-existent task")
    void updateNonExistentTask() {
        when(taskRepository.findById(anyLong())).thenReturn(Optional.empty());
        try {
            taskService.updateTask(1L, task);
        } catch (RuntimeException e) {
            assertThat(e.getMessage()).isEqualTo("Tarefa não encontrada");
        }
    }

    @Test
    @DisplayName("Delete a task by ID")
    void deleteTask() {
        doNothing().when(taskRepository).deleteById(anyLong());
        taskService.deleteTask(1L);
        verify(taskRepository, times(1)).deleteById(1L);
    }
}