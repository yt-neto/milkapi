package com.oficinadobrito.api.Users;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oficinadobrito.api.Config.SecurityConfig;
import com.oficinadobrito.api.Controllers.AuthenticateController;
import com.oficinadobrito.api.Controllers.Dtos.LoginDto;
import com.oficinadobrito.api.Entities.UserRole;
import com.oficinadobrito.api.Entities.Usuario;
import com.oficinadobrito.api.Repositories.UsuarioRepository;
import com.oficinadobrito.api.Services.JwtTokenService;
import com.oficinadobrito.api.Services.UsuarioService;
import com.oficinadobrito.api.Services.VendedorServices;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthenticateController.class)
@AutoConfigureMockMvc
@Import(SecurityConfig.class)
class UserControllerTests {

    @MockBean
    private UsuarioService userService;

    @MockBean
    private VendedorServices vendedorServices;

    @MockBean
    JwtTokenService jwtTokenService;

    @MockBean
    UsuarioRepository userRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private LoginDto login;
    private String jwt;
    private Usuario user;

    @BeforeEach
    void setUp() {
        login = new LoginDto("example@gmail.com", "passwordExample000#");
        user = new Usuario("novousuario", "novoUsuario@gmail.com", "passwordExample000", UserRole.USER);
        jwt = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJBbGd1bWNvbnRldWRvRnVsZXJhIiwibmFtZSI6IlRlc3RlIiwiaWF0IjoxNTE2MjM5MDIyfQ.eUWumPqZdQsSVKD3hc2PgZm2OTixW6uqVD9OMu6QLI4";
    }

    void mockJwtSetter(){
        this.user.setToken("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJBbGd1bWNvbnRldWRvRnVsZXJhIiwibmFtZSI6IlRlc3RlIiwiaWF0IjoxNTE2MjM5MDIyfQ.eUWumPqZdQsSVKD3hc2PgZm2OTixW6uqVD9OMu6QLI4");
    }

    @DisplayName("When the register method returns a user registered in the system")
    @Test
    void testIfTheRegisterMethodReturnsAUserRegisteredInTheSystem() throws Exception {
        // Given - Arrange
        given(userService.createNewUser(any(Usuario.class))).willReturn(user);

        // When  - Act
        ResultActions res = mockMvc.perform(post("/auth/register").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(user)));

        // Then  - Assert
        res.andExpect(status().isCreated()).andExpect(jsonPath("$.username", is(user.getUsername())))
                .andExpect(jsonPath("$.email", is(user.getEmail())))
                .andExpect(jsonPath("$.role", is(user.getRole().toString())));
        // with print -- use res.andDo(print()).andExpect(status().isCreated())...
    }

    @DisplayName("When Login MethodReturns An JwtGerated For System")
    @Test
    void testWhen_LoginMethod_ReturnsAnJwtGeratedForSystem() throws Exception {
        // Given - Arrange
        given(userService.authenticarusuario(any(LoginDto.class))).willReturn(jwt);

        // When  - Act
        mockJwtSetter();
        ResultActions res = mockMvc.perform(post("/auth/login").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(this.login)));

        // Then  - Assert
        res.andExpect(status().isOk()).andExpect(jsonPath("$.token", is(user.getToken())));
    }

    @DisplayName("When Login NotAuthorized MethodReturns An JwtGerated For System")
    @Test
    void testWhen_LoginMethodNotAuthorized_ReturnsAnJwtGeratedForSystem() throws Exception {
        // Given - Arrange
        given(userService.authenticarusuario(any(LoginDto.class))).willReturn("");

        // When  - Act
        ResultActions res = mockMvc.perform(post("/auth/login").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(this.login)));

        // Then  - Assert
        res.andExpect(status().isUnauthorized()).andExpect(jsonPath("$.token", is("User with credentials not found or invalid")));
    }
}