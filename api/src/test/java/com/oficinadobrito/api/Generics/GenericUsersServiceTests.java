package com.oficinadobrito.api.Generics;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import org.mockito.MockedStatic;
import com.oficinadobrito.api.Controllers.Customvalidators.Classes.EmailValidator;
import com.oficinadobrito.api.Entities.Usuario;
import com.oficinadobrito.api.Repositories.Generics.IGenericIdUUIDRepository;
import com.oficinadobrito.api.Repositories.UsuarioRepository;
import com.oficinadobrito.api.Services.Generics.GenericUsersService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
class GenericUsersServiceTests {
    @Mock
    private IGenericIdUUIDRepository<Usuario> genericRepository;

    @Mock
    private UsuarioRepository userRepository;

    @InjectMocks
    private GenericUsersService<Usuario> genericUsersService;

    private static MockedStatic<EmailValidator> mockedValidator;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        usuario = new Usuario();
        usuario.setEmail("valid.email@example.com");
        mockedValidator = mockStatic(EmailValidator.class);
    }

    @AfterEach
    void tearDown() {
        mockedValidator.close();
    }

    @Test
    @DisplayName("Create resource - Invalid email or email already exists")
    void createResourceInvalidEmailOrEmailExists() {
        mockedValidator.when(() -> EmailValidator.isValidEmail(usuario.getEmail())).thenReturn(true);
        when(userRepository.findByEmail(usuario.getEmail())).thenReturn(Optional.of(usuario));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            genericUsersService.createResource(usuario);
        });

        assertThat(exception.getMessage(), is("The email provided is in the wrong format or a user with that email is already in this application"));
        verify(userRepository, times(1)).findByEmail(usuario.getEmail());
        verify(genericRepository, never()).save(any());
    }

    @Test
    @DisplayName("Delete resource - Success")
    void deleteResource() {
        String id = "valid-uuid";
        when(genericRepository.findById(id)).thenReturn(Optional.of(usuario));

        boolean isDeleted = genericUsersService.deleteteResource(id);

        verify(genericRepository, times(1)).findById(id);
        verify(genericRepository, times(1)).delete(usuario);
        assertThat(isDeleted, is(true));
    }

    @Test
    @DisplayName("Delete resource - Resource not found")
    void deleteResourceNotFound() {
        String id = "invalid-uuid";
        when(genericRepository.findById(id)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            genericUsersService.deleteteResource(id);
        });

        assertThat(exception.getMessage(), is("Resourcer not found with id " + id));
        verify(genericRepository, times(1)).findById(id);
        verify(genericRepository, never()).delete(any());
    }

    @Test
    @DisplayName("Update resource - Success")
    void updateResource() {
        String id = "valid-uuid";
        when(genericRepository.findById(id)).thenReturn(Optional.of(usuario));
        when(genericRepository.save(usuario)).thenReturn(usuario);

        Usuario updatedUsuario = genericUsersService.updateResource(id, usuario);

        verify(genericRepository, times(1)).findById(id);
        verify(genericRepository, times(1)).save(usuario);
        assertThat(updatedUsuario, is(notNullValue()));
        assertThat(updatedUsuario, is(usuario));
    }

    @Test
    @DisplayName("Update resource - Resource not found")
    void updateResourceNotFound() {
        String id = "invalid-uuid";
        when(genericRepository.findById(id)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            genericUsersService.updateResource(id, usuario);
        });

        assertThat(exception.getMessage(), is("Resourcer not found with id " + id));
        verify(genericRepository, times(1)).findById(id);
        verify(genericRepository, never()).save(any());
    }

    @Test
    @DisplayName("Get resource by ID - Success")
    void getResourceById() {
        String id = "valid-uuid";
        when(genericRepository.findById(id)).thenReturn(Optional.of(usuario));

        Usuario foundUsuario = genericUsersService.getResourceById(id);

        verify(genericRepository, times(1)).findById(id);
        assertThat(foundUsuario, is(notNullValue()));
        assertThat(foundUsuario, is(usuario));
    }

    @Test
    @DisplayName("Get resource by ID - Resource not found")
    void getResourceByIdNotFound() {
        String id = "invalid-uuid";
        when(genericRepository.findById(id)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            genericUsersService.getResourceById(id);
        });

        assertThat(exception.getMessage(), is("Resourcer not found with id " + id));
        verify(genericRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("Get all resources")
    void getAllResources() {
        List<Usuario> resources = List.of(usuario, new Usuario());
        when(genericRepository.findAll()).thenReturn(resources);

        List<Usuario> foundResources = genericUsersService.getAllResources();

        verify(genericRepository, times(1)).findAll();
        assertThat(foundResources, is(notNullValue()));
        assertThat(foundResources, hasSize(2));
        assertThat(foundResources, contains(usuario, new Usuario()));
    }
}
