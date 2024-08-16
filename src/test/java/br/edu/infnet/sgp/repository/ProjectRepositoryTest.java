package br.edu.infnet.sgp.repository;

import br.edu.infnet.sgp.domain.entity.Project;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ProjectRepositoryTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ProjectRepository projectRepository;

    private Project project;

    @BeforeEach
    void setUp() {
        project = new Project();
        project.setName("Test Project");
        project = entityManager.persistAndFlush(project);
    }

    @Test
    @DisplayName("Find project by ID")
    void findById() {
        Optional<Project> foundProject = projectRepository.findById(project.getId());
        assertThat(foundProject).isPresent();
        assertThat(foundProject.get().getName()).isEqualTo("Test Project");
    }

    @Test
    @DisplayName("Find project by non-existent ID")
    void findByNonExistentId() {
        Optional<Project> foundProject = projectRepository.findById(-1L);
        assertThat(foundProject).isNotPresent();
    }

    @Test
    @DisplayName("Save a new project")
    void saveProject() {
        Project newProject = new Project();
        newProject.setName("New Project");
        Project savedProject = projectRepository.save(newProject);
        assertThat(savedProject.getId()).isNotNull();
        assertThat(savedProject.getName()).isEqualTo("New Project");
    }

    @Test
    @DisplayName("Delete a project")
    void deleteProject() {
        projectRepository.delete(project);
        Optional<Project> foundProject = projectRepository.findById(project.getId());
        assertThat(foundProject).isNotPresent();
    }
}