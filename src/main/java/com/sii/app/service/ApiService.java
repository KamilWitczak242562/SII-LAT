package com.sii.app.service;

import com.sii.app.model.Product;
import com.sii.app.model.Purchase;

import java.util.List;

public interface ApiService<T> {
    T create(T entity);

    List<T> getAll();

    T update(T entity, Long id);

    boolean checkIfExist(Long id);

    boolean checkIfExist(T entity);
}
