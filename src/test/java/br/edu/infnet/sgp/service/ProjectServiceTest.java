package br.edu.infnet.sgp.service;

import br.edu.infnet.sgp.domain.entity.Project;
import br.edu.infnet.sgp.repository.ProjectRepository;
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

class ProjectServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private ProjectService projectService;

    private Project project;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        project = new Project();
        project.setId(1L);
        project.setName("Test Project");
    }

    @Test
    @DisplayName("Get all projects")
    void getAllProjects() {
        when(projectRepository.findAll()).thenReturn(Arrays.asList(project));
        List<Project> projects = projectService.getAllProjects();
        assertThat(projects).hasSize(1);
        assertThat(projects.get(0).getName()).isEqualTo("Test Project");
    }

    @Test
    @DisplayName("Create a new project")
    void createProject() {
        when(projectRepository.save(any(Project.class))).thenReturn(project);
        Project createdProject = projectService.createProject(project);
        assertThat(createdProject.getId()).isEqualTo(1L);
        assertThat(createdProject.getName()).isEqualTo("Test Project");
    }

    @Test
    @DisplayName("Update an existing project")
    void updateProject() {
        when(projectRepository.findById(anyLong())).thenReturn(Optional.of(project));
        when(projectRepository.save(any(Project.class))).thenReturn(project);
        Project updatedProject = projectService.updateProject(1L, project);
        assertThat(updatedProject.getName()).isEqualTo("Test Project");
    }

    @Test
    @DisplayName("Update a non-existent project")
    void updateNonExistentProject() {
        when(projectRepository.findById(anyLong())).thenReturn(Optional.empty());
        try {
            projectService.updateProject(1L, project);
        } catch (RuntimeException e) {
            assertThat(e.getMessage()).isEqualTo("Projeto não encontrado");
        }
    }

    @Test
    @DisplayName("Delete a project by ID")
    void deleteProject() {
        doNothing().when(projectRepository).deleteById(anyLong());
        projectService.deleteProject(1L);
        verify(projectRepository, times(1)).deleteById(1L);
    }
}