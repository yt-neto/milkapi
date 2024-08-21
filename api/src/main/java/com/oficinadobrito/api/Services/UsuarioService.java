package com.oficinadobrito.api.Services;

import com.oficinadobrito.api.Controllers.Customvalidators.Classes.EmailValidator;
import com.oficinadobrito.api.Controllers.Dtos.LoginDto;
import com.oficinadobrito.api.Entities.Usuario;
import com.oficinadobrito.api.Repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
    @Autowired
    private  JwtTokenService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario createNewUser(Usuario user){
        var usuario = this.usuarioRepository.findByEmail(user.getEmail());
        if(!EmailValidator.isValidEmail(user.getEmail()) || usuario.isPresent()){
            throw new IllegalArgumentException("The email provided is in the wrong format or a user with that email is already in this application");
        }
        return this.usuarioRepository.save(user);
    }

   public String authenticarusuario(LoginDto usuario){
       var usernamePssword = new UsernamePasswordAuthenticationToken(usuario.getEmail(), usuario.getPassword());
       try {
           Authentication authentication = this.authenticationManager.authenticate(usernamePssword);
           return jwtService.generateToken((Usuario) authentication.getPrincipal());
       } catch (BadCredentialsException e) {
           return "";
       } catch (AuthenticationException e) {
           // Outras exceções de autenticação
           return "";
       }
   }
}
