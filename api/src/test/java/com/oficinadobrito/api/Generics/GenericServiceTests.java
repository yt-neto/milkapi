package com.oficinadobrito.api.Generics;

import com.oficinadobrito.api.Repositories.Generics.IGenericRepository;
import com.oficinadobrito.api.Services.Generics.GenericService;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
class GenericServiceTests {
    @Mock
    private IGenericRepository<Object> genericRepository;

    @InjectMocks
    private GenericService<Object> genericService;

    private Object resource;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        resource = new Object();
    }

    @DisplayName("Create resource - Success")
    @Test
    void createResource() {
        //Given - Arrange
        when(genericRepository.save(resource)).thenReturn(resource);

        //When - Act
        Object createdResource = genericService.createResource(resource);

        //Then - Assert
        verify(genericRepository, times(1)).save(resource);
        assertThat(createdResource, is(notNullValue()));
        assertThat(createdResource, is(resource));
    }

    @DisplayName("Delete resource - Success")
    @Test
    void deleteResource() {
        //Given - Arrange
        long id = 1L;
        when(genericRepository.findById(id)).thenReturn(Optional.of(resource));

        //When - Act
        boolean isDeleted = genericService.deleteteResource(id);

        //Then - Assert
        verify(genericRepository, times(1)).findById(id);
        verify(genericRepository, times(1)).delete(resource);
        assertThat(isDeleted, is(true));
    }

    @DisplayName("Delete resource - Resource not found")
    @Test
    void deleteResourceNotFound() {
        //Given - Arrange
        long id = 1L;
        when(genericRepository.findById(id)).thenReturn(Optional.empty());

        //When - Act
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            genericService.deleteteResource(id);
        });

        //Then - Assert
        assertThat(exception.getMessage(), is("Resourcer not found with id " + id));
        verify(genericRepository, times(1)).findById(id);
        verify(genericRepository, never()).delete(any());
    }

    @DisplayName("Update resource - Success")
    @Test
    void updateResource() {
        //Given - Arrange
        long id = 1L;
        when(genericRepository.findById(id)).thenReturn(Optional.of(resource));
        when(genericRepository.save(resource)).thenReturn(resource);

        //When - Act
        Object updatedResource = genericService.updateResource(id, resource);

        //Then - Assert
        verify(genericRepository, times(1)).findById(id);
        verify(genericRepository, times(1)).save(resource);
        assertThat(updatedResource, is(notNullValue()));
        assertThat(updatedResource, is(resource));
    }

    @DisplayName("Update resource - Resource not found")
    @Test
    void updateResourceNotFound() {
        //Given - Arrange
        long id = 1L;
        when(genericRepository.findById(id)).thenReturn(Optional.empty());

        //When - Act
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            genericService.updateResource(id, resource);
        });

        //Then - Assert
        assertThat(exception.getMessage(), is("Resourcer not found with id " + id));
        verify(genericRepository, times(1)).findById(id);
        verify(genericRepository, never()).save(any());
    }

    @DisplayName("Get resource by ID - Success")
    @Test
    void getResourceById() {
        //Given - Arrange
        long id = 1L;
        when(genericRepository.findById(id)).thenReturn(Optional.of(resource));

        //When - Act
        Object foundResource = genericService.getResourceById(id);
        
        //Then - Assert
        verify(genericRepository, times(1)).findById(id);
        assertThat(foundResource, is(notNullValue()));
        assertThat(foundResource, is(resource));
    }

    @DisplayName("Get resource by ID - Resource not found")
    @Test
    void getResourceByIdNotFound() {
        //Given - Arrange
        long id = 1L;
        when(genericRepository.findById(id)).thenReturn(Optional.empty());

        //When - Act
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            genericService.getResourceById(id);
        });

        //Then - Assert
        assertThat(exception.getMessage(), is("Resourcer not found with id " + id));
        verify(genericRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("Get all resources")
    void getAllResources() {
        //Given - Arrange
        Object resource2 = new Object();
        List<Object> resources = List.of(resource, resource2);
        when(genericRepository.findAll()).thenReturn(resources);

        //When - Act
        List<Object> foundResources = genericService.getAllResources();

        //Then - Assert
        verify(genericRepository, times(1)).findAll();
        assertThat(foundResources, is(notNullValue()));
        assertThat(foundResources, hasSize(2));
        assertThat(foundResources, contains(resource, resource2));
    }
}
