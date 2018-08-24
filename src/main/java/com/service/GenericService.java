package com.service;

import java.util.List;

public interface GenericService<T> {
    T save(T entity);

    T update(T entity);

    void delete(T entity);

    void deleteInBatch(List<T> entities);

    T find(Long id);

    List<T> findAll();
}
