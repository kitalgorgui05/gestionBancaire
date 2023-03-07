package com.gestion.banking.services;

import java.util.List;

public interface AbstractService<T> {
    String save(T dto);

    List<T> findAll();

    T findById(String id);

    void  delete(String id);

}
