package com.oficinadobrito.api.Controllers;

import com.oficinadobrito.api.Controllers.Dtos.Creates.UsuarioCreateDto;
import com.oficinadobrito.api.Controllers.Dtos.LoginDto;
import com.oficinadobrito.api.Controllers.Dtos.TokenResponse;
import com.oficinadobrito.api.Entities.Usuario;
import com.oficinadobrito.api.Entities.Vendedor;
import com.oficinadobrito.api.Services.UsuarioService;
import com.oficinadobrito.api.Services.VendedorServices;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;

import java.util.function.Supplier;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Autenticação de usuarios", description = "All endpoints related to the resource")
@RestController
@RequestMapping("/auth")
public class AuthenticateController {

    private final UsuarioService userService;
    private final VendedorServices vendedorServices;

    public AuthenticateController(UsuarioService userService, VendedorServices vendedorServices) {
        this.userService = userService;
        this.vendedorServices = vendedorServices;
    }

    @PermitAll
    @PostMapping("/login")
    public ResponseEntity<TokenResponse> autenticar(@RequestBody LoginDto usuario) {
        var token = this.userService.authenticarusuario(usuario);
        if (token.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new TokenResponse("User with credentials not found or invalid"));
        }
        TokenResponse tokenResponse = new TokenResponse(token);
        return ResponseEntity.ok().body(tokenResponse);
    }

    @PermitAll
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UsuarioCreateDto userDto) {
        Usuario user = Usuario.toEntity(userDto);
        try {
            switch (user.getRole()) {
                case USER:
                    ResponseEntity<String> userResponse = handleResourceCreation(() -> this.userService.createNewUser(user));
                    if (userResponse != null)
                        return userResponse;
                    break;
                case VENDEDOR:
                    ResponseEntity<String> vendedorResponse = handleResourceCreation(() -> this.vendedorServices.createResource(Vendedor.copyByUsuario(user)));
                    if (vendedorResponse != null)
                        return vendedorResponse;
                    break;
                default:
                    return ResponseEntity.badRequest().body("Role inválid");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao criar usuário: " + e.getMessage());
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    private ResponseEntity<String> handleResourceCreation(Supplier<?> createAction) {
        try {
            createAction.get();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return null;
    }
}
