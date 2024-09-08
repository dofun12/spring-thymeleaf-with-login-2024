package org.lemanoman.springthymeleaf.service;

import java.util.List;
import java.util.Optional;

public interface GenericService<T, ID> {
    T save(T entity);
    List<T> findAll();
    Optional<T> findById(ID id);
    void deleteById(ID id);
    void delete(T entity);
}