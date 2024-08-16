package br.edu.infnet.sgp.repository;

import br.edu.infnet.sgp.domain.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUsername("TestUser");
        user.setEmail("testuser@example.com");
        user = entityManager.persistAndFlush(user);
    }

    @Test
    @DisplayName("Find user by ID")
    void findById() {
        Optional<User> foundUser = userRepository.findById(user.getId());
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getUsername()).isEqualTo("TestUser");
        assertThat(foundUser.get().getEmail()).isEqualTo("testuser@example.com");
    }

    @Test
    @DisplayName("Find user by non-existent ID")
    void findByNonExistentId() {
        Optional<User> foundUser = userRepository.findById(-1L);
        assertThat(foundUser).isNotPresent();
    }

    @Test
    @DisplayName("Save a new user")
    void saveUser() {
        User newUser = new User();
        newUser.setUsername("NewUser");
        newUser.setEmail("newuser@example.com");
        User savedUser = userRepository.save(newUser);
        assertThat(savedUser.getId()).isNotNull();
        assertThat(savedUser.getUsername()).isEqualTo("NewUser");
        assertThat(savedUser.getEmail()).isEqualTo("newuser@example.com");
    }

    @Test
    @DisplayName("Delete a user")
    void deleteUser() {
        userRepository.delete(user);
        Optional<User> foundUser = userRepository.findById(user.getId());
        assertThat(foundUser).isNotPresent();
    }
}