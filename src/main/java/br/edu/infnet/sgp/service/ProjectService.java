package br.edu.infnet.sgp.service;


import br.edu.infnet.sgp.domain.entity.Project;
import br.edu.infnet.sgp.repository.ProjectRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public Project createProject(Project project) {
        return projectRepository.save(project);
    }

    public Project updateProject(Long id, Project projectDetails) {
        return projectRepository.findById(id)
                .map(existingProject -> {
                    existingProject.setDescription(projectDetails.getDescription());
                    existingProject.setName(projectDetails.getName());
                    existingProject.setTasks(projectDetails.getTasks());
                    return projectRepository.save(existingProject);
                })
                .orElseThrow( () -> new RuntimeException("Projeto não encontrado"));
    }

    public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }
}
