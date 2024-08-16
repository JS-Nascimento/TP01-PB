package br.edu.infnet.sgp.repository;

import br.edu.infnet.sgp.domain.entity.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class TaskRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TaskRepository taskRepository;

    private Task task;

    @BeforeEach
    void setUp() {
        task = new Task();
        task.setName("Test Task");
        task = entityManager.persistAndFlush(task);
    }

    @Test
    @DisplayName("Find task by ID")
    void findById() {
        Optional<Task> foundTask = taskRepository.findById(task.getId());
        assertThat(foundTask).isPresent();
        assertThat(foundTask.get().getName()).isEqualTo("Test Task");
    }

    @Test
    @DisplayName("Find task by non-existent ID")
    void findByNonExistentId() {
        Optional<Task> foundTask = taskRepository.findById(-1L);
        assertThat(foundTask).isNotPresent();
    }

    @Test
    @DisplayName("Save a new task")
    void saveTask() {
        Task newTask = new Task();
        newTask.setName("New Task");
        Task savedTask = taskRepository.save(newTask);
        assertThat(savedTask.getId()).isNotNull();
        assertThat(savedTask.getName()).isEqualTo("New Task");
    }

    @Test
    @DisplayName("Delete a task")
    void deleteTask() {
        taskRepository.delete(task);
        Optional<Task> foundTask = taskRepository.findById(task.getId());
        assertThat(foundTask).isNotPresent();
    }
}