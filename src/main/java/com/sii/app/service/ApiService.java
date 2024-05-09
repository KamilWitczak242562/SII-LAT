package com.sii.app.service;

import java.util.List;

public interface ApiService<T> {
    T create(T entity);

    List<T> getAll();

    boolean checkIfExist(Long id);

    boolean checkIfExist(T entity);
}
