package com.oficinadobrito.api.Services.Interfaces;

import java.util.List;

public interface IGenericUsersService<T> {
    T createResource(T recurso);
    boolean deleteteResource(String id);
    T updateResource(String id, T recurso);
    T getResourceById(String id);
    List<T> getAllResources();

}
