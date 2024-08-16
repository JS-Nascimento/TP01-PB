package br.edu.infnet.sgp.controller;

import br.edu.infnet.sgp.domain.entity.Project;
import br.edu.infnet.sgp.service.ProjectService;
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

class ProjectControllerTest {

    @Mock
    private ProjectService projectService;

    @InjectMocks
    private ProjectController projectController;

    private MockMvc mockMvc;
    private Project project;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(projectController).build();
        project = new Project();
        project.setId(1L);
        project.setName("Test Project");
    }

    @Test
    @DisplayName("Get all projects")
    void getAllProjects() throws Exception {
        List<Project> projects = Arrays.asList(project);
        when(projectService.getAllProjects()).thenReturn(projects);

        mockMvc.perform(get("/projects"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Test Project"));
    }

    @Test
    @DisplayName("Create a new project")
    void createProject() throws Exception {
        when(projectService.createProject(any(Project.class))).thenReturn(project);

        mockMvc.perform(post("/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Test Project\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Project"));
    }

    @Test
    @DisplayName("Update an existing project")
    void updateProject() throws Exception {
        when(projectService.updateProject(anyLong(), any(Project.class))).thenReturn(project);

        mockMvc.perform(put("/projects/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Updated Project\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Project"));
    }


    @Test
    @DisplayName("Delete a project by ID")
    void deleteProject() throws Exception {
        doNothing().when(projectService).deleteProject(anyLong());

        mockMvc.perform(delete("/projects/1"))
                .andExpect(status().isOk());

        verify(projectService, times(1)).deleteProject(1L);
    }
}