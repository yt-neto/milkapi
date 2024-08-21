package com.oficinadobrito.api.Repositories.Generics;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface IGenericRepository<T> extends JpaRepository<T, Long> {
}
