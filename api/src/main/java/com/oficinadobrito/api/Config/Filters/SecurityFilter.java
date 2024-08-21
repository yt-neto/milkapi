package com.oficinadobrito.api.Config.Filters;

import com.oficinadobrito.api.Repositories.UsuarioRepository;
import com.oficinadobrito.api.Services.JwtTokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    private final UsuarioRepository userRepository;
    private final JwtTokenService jwtService;
    Authentication authentication;

    public SecurityFilter(UsuarioRepository userRepository,JwtTokenService jwtService){
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)throws ServletException, IOException {
        var token = this.recoverToken(request);

        if(token != null){
            var subject = jwtService.validateToken(token);
            Optional<UserDetails> user = userRepository.findByEmail(subject);
            if(user.isPresent()){
                UserDetails usuario = user.get();
                this.authentication = new UsernamePasswordAuthenticationToken( user,null, usuario.getAuthorities());
            }
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request){
        var authHeader = request.getHeader("Authorization");
        if(authHeader == null) return null;
        return authHeader.replace("Bearer ","");

    }
}