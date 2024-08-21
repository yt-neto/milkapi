package com.oficinadobrito.api.Services.Generics;

import com.oficinadobrito.api.Controllers.Customvalidators.Classes.EmailValidator;
import com.oficinadobrito.api.Entities.Usuario;
import com.oficinadobrito.api.Repositories.Generics.IGenericIdUUIDRepository;
import com.oficinadobrito.api.Repositories.UsuarioRepository;
import com.oficinadobrito.api.Services.Interfaces.IGenericUsersService;

import java.util.List;
import java.util.Optional;

public class GenericUsersService<T extends Usuario> implements IGenericUsersService<T> {
    IGenericIdUUIDRepository<T> genericRepository;
    UsuarioRepository userRepository;

    public GenericUsersService(IGenericIdUUIDRepository<T> genericRepository,UsuarioRepository userRepository) {
        this.genericRepository = genericRepository;
        this.userRepository = userRepository;
    }

    public T createResource(T recurso){
        var usuario = this.userRepository.findByEmail(recurso.getEmail());
        if(!EmailValidator.isValidEmail(recurso.getEmail()) || usuario.isPresent()){
            throw new IllegalArgumentException("The email provided is in the wrong format or a user with that email is already in this application");
        }
        return this.genericRepository.save(recurso);
    }

    @Override
    public boolean deleteteResource(String id) {
        Optional<T> resourceOptional = this.genericRepository.findById(id);
        if (resourceOptional.isPresent()) {
            this.genericRepository.delete(resourceOptional.get());
            return true;
        } else {
            throw new RuntimeException("Resourcer not found with id " + id);
        }
    }

    @Override
    public T updateResource(String id,T recurso) {
        Optional<T> resourceOptional = this.genericRepository.findById(id);
        if (resourceOptional.isPresent()) {
            return this.genericRepository.save(recurso);
        } else {
            throw new RuntimeException("Resourcer not found with id " + id);
        }
    }

    @Override
    public T getResourceById(String id) {
        Optional<T> resourceOptional = this.genericRepository.findById(id);
        if (resourceOptional.isPresent()) {
            return resourceOptional.get();
        } else {
            throw new RuntimeException("Resourcer not found with id " + id);
        }
    }

    @Override
    public List<T> getAllResources() {
        return this.genericRepository.findAll();
    }
}
