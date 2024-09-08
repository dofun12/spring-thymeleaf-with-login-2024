package org.lemanoman.springthymeleaf.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public class GenericServiceImpl<T, D> implements GenericService<T, D> {

    private final JpaRepository<T, D> repository;

    public GenericServiceImpl(JpaRepository<T, D> repository) {
        this.repository = repository;
    }

    @Override
    public T save(T entity) {
        return repository.save(entity);
    }

    @Override
    public List<T> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<T> findById(D d) {
        return Optional.empty();
    }

    @Override
    public void deleteById(D d) {
        repository.deleteById(d);
    }

    @Override
    public void delete(T entity) {
        repository.delete(entity);
    }
}