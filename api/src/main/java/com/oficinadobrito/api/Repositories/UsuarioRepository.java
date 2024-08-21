package com.oficinadobrito.api.Repositories;

import com.oficinadobrito.api.Entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, String> {
    Optional<Usuario> findByUsername(String username);
    Optional<UserDetails> findByEmail(String email);
}
