package com.oficinadobrito.api.Services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.oficinadobrito.api.Entities.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.security.core.GrantedAuthority;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.stream.Collectors;

@Service
public class JwtTokenService {
    @Value("${jwt.secret}")
    private String SecretKey;

    public String generateToken(Usuario user) {
        try {
            String scopes = user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(" "));
            Algorithm algorithm = Algorithm.HMAC256(this.SecretKey);
            return JWT.create().withIssuer("auth-api").withSubject(user.getEmail()).withExpiresAt(generateExpiration()).withClaim("Claims", scopes).sign(algorithm);
        } catch (JWTCreationException e) {
            throw new RuntimeException("Erro while generate token Jwt", e);
        }
    }

    private Instant generateExpiration() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }

    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SecretKey);
            return JWT.require(algorithm).withIssuer("auth-api").build().verify(token).getSubject();
        } catch (JWTVerificationException e) {
            return "";
        }
    }
}
