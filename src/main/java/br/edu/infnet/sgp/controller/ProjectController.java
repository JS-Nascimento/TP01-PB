package br.edu.infnet.sgp.controller;

import br.edu.infnet.sgp.domain.entity.Project;
import br.edu.infnet.sgp.service.ProjectService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/projects")
@io.swagger.v3.oas.annotations.tags.Tag(name = "Project API", description = "Gerenciamento de Projetos")
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    @GetMapping
    @io.swagger.v3.oas.annotations.Operation(summary = "Listar todos os projetos")
    public List<Project> getAllProjects() {
        return projectService.getAllProjects();
    }

    @PostMapping
    @io.swagger.v3.oas.annotations.Operation(summary = "Criar um novo projeto")
    public Project createProject(@RequestBody Project project) {
        return projectService.createProject(project);
    }

    @PutMapping("/{id}")
    @io.swagger.v3.oas.annotations.Operation(summary = "Atualizar um projeto existente")
    public Project updateProject(@PathVariable Long id, @RequestBody Project projectDetails) {
        return projectService.updateProject(id, projectDetails);
    }

    @DeleteMapping("/{id}")
    @io.swagger.v3.oas.annotations.Operation(summary = "Deletar um projeto")
    public void deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
    }
}
