package br.edu.infnet.sgp.controller;

import br.edu.infnet.sgp.domain.entity.User;
import br.edu.infnet.sgp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequestMapping("/users")
@Tag(name = "User API", description = "API para gerenciamento de usuários")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    @Operation(summary = "Listar todos os usuários")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping
    @Operation(summary = "Criar um novo usuário")
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar um usuário existente")
    public User updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        return userService.updateUser(id, userDetails);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar um usuário")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}