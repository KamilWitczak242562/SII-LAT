package com.sii.app.service;

import java.util.List;

public interface ApiService<T> {
    T create(T entity);

    T update(T entity, Long id);

    List<T> getAll();

    boolean delete(Long id);

    boolean checkIfExist(Long id);

    boolean checkIfExist(T entity);
}
