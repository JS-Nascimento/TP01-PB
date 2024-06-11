package br.edu.infnet.sgp.service;

import br.edu.infnet.sgp.domain.entity.Task;
import br.edu.infnet.sgp.repository.TaskRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    public Task updateTask(Long id, Task taskDetails) {
        return taskRepository.findById(id)
                .map(existingTask -> {
                    existingTask.setName(taskDetails.getName());
                    existingTask.setStatus(taskDetails.getStatus());
                    existingTask.setProject(taskDetails.getProject());
                    return taskRepository.save(existingTask);
                })
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada"));
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }
}
