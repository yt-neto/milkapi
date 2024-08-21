package com.oficinadobrito.api.Users;

import com.oficinadobrito.api.Entities.UserRole;
import com.oficinadobrito.api.Entities.Usuario;
import com.oficinadobrito.api.Repositories.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTests {
    @MockBean
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioRepository userRepository;

    private Usuario usuario;
    private Usuario userSaved;

    @BeforeEach
    void setUp(){
        this.usuario = new Usuario("testuser","testemail@example.com","Teste123*", UserRole.USER);
    }

    @DisplayName("Test Usuario Repository Extends Jpa Repository")
    @Test
    void testUsuarioRepositoryExtendsJpaRepository() {
        //Given - Arrange / When - Act
        boolean isJpaRepository = UsuarioRepository.class.getGenericInterfaces()[0] instanceof ParameterizedType &&
                ((ParameterizedType) UsuarioRepository.class.getGenericInterfaces()[0]).getRawType().equals(JpaRepository.class);

        //Then - Assert
        assertTrue(isJpaRepository, () -> "User repository is not extending Jparepository");
    }

    @DisplayName("Test FindByUsername Exists")
    @Test
    void testFindByUsernameExists() throws NoSuchMethodException {
        Method method = UsuarioRepository.class.getMethod("findByUsername", String.class);
        assertNotNull(method, ()-> "findByUsername method should exist in UsuarioRepository");
    }

    @DisplayName("Test FindByEmail Exists")
    @Test
    void testFindByEmailExists() throws NoSuchMethodException {
        Method method = UsuarioRepository.class.getMethod("findByEmail", String.class);
        assertNotNull(method, ()->"findByEmail method should exist in UsuarioRepository");
    }

    @DisplayName("The operation of the method being FindByUsername")
    @Test
    void testFindByUsername() {
        // Given - Arrange
        usuario.setUsername("testOutro");
        this.userSaved = userRepository.save(this.usuario);

        // When - Act
        Optional<Usuario> found = userRepository.findByUsername("testOutro");

        // Then - Assert
        assertTrue(found.isPresent());
        assertEquals("testOutro", found.get().getUsername(), () -> "user with username not found");
    }


    @DisplayName("The operation of the method being FindByEmail")
    @Test
    void testFindByEmail() {
        //Given - Arrange
        this.userSaved = userRepository.save(this.usuario);

        //When - Act
        Optional<UserDetails> found = userRepository.findByEmail("testemail@example.com");

        //Then - Assert
        assertTrue(found.isPresent(), ()-> "user with not found");
        assertEquals(userSaved.getUsername(), found.get().getUsername(), () -> "user with email not found");
    }

    @Test
    @DisplayName("When no user is found by email  return empty")
    void testFindByEmail_NotFound() {
        // Given - Arrange
        String nonExistentEmail = "nonexistent@example.com";

        // When - Act
        Optional<UserDetails> found = userRepository.findByEmail(nonExistentEmail);

        // Then - Assert
        assertFalse(found.isPresent(), () -> "This email must not appear in the bank in any way");
    }
}
