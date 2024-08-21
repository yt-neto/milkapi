package com.oficinadobrito.api.Config.Listeners;

import com.oficinadobrito.api.Entities.Usuario;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UsuarioListener {

    private final PasswordEncoder passwordEncoder;

    public UsuarioListener(PasswordEncoder passwordEncoder){
        this.passwordEncoder = passwordEncoder;
    }

    @PrePersist
    @PreUpdate
    public void encryptPassword(Usuario usuario) {
        if (usuario.getPassword() != null && !usuario.getPassword().isEmpty()) {
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        }
    }
}
