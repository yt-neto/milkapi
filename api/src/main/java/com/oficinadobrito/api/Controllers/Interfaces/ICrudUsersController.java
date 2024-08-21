package com.oficinadobrito.api.Controllers.Interfaces;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;

public interface ICrudUsersController<T,TCreateDto,TUpdateDto> {
    ResponseEntity<List<T>> getAllResource();
    ResponseEntity<T> updateResource(@PathVariable("id") String id, @RequestBody TUpdateDto resource);
    ResponseEntity<Void> deleteResourceById(@PathVariable("id") String id);
    ResponseEntity<T> getResourceById(@PathVariable("id") String id);
}
