package com.oficinadobrito.api.Users;

import com.oficinadobrito.api.Entities.UserRole;
import com.oficinadobrito.api.Entities.Usuario;
import com.oficinadobrito.api.Repositories.UsuarioRepository;
import com.oficinadobrito.api.Services.JwtTokenService;
import com.oficinadobrito.api.Services.UsuarioService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
class UserServiceTests {

    @Mock
    private JwtTokenService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    private Usuario user;

    @BeforeEach
    void setUp() {
        user = new Usuario("Jonas", "Jonas@gmail.com","JonasGuty53#", UserRole.USER);
    }

    void mockIdSetter() {
        String uuid = UUID.randomUUID().toString();
        this.user.setUsuarioId(uuid);
    }

    @DisplayName("When create Usuario success")
    @Test
    void testUsuarioCreate_WhenSuccess_ReturnUsuarioObject() {
        //Given - Arrange / When - Act
        mockIdSetter();
        when(this.usuarioService.createNewUser(user)).thenReturn(user); // mock method
        var actual = this.usuarioService.createNewUser(user);

        //Then  - Assert
        Assertions.assertNotNull(actual, () -> "The user is successfully created, it is not being returned");
        Assertions.assertEquals(this.user.getUsuarioId().toUpperCase(),actual.getUsuarioId().toUpperCase(), () -> "The user created successfully does not have an ID");
    }

    @DisplayName("when the user is created wrongly")
    @Test
    void testUsuarioCreate_WhenFail_ReturnUsuarioObject() {
        //Given - Arrange / When - Act
        user.setEmail(" ");
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            this.usuarioService.createNewUser(user);
        });
        Assertions.assertNotNull(thrown,()->"The section is not being released");
        Assertions.assertEquals("The email provided is in the wrong format or a user with that email is already in this application", thrown.getMessage());
    }
}
