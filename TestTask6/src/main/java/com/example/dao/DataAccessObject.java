package com.example.dao;

import java.util.List;

public interface DataAccessObject<T> {
    void insert(T value);

    void insertAll(List<T> value);

    void delete(T value);

    void deleteAll();

    void update(T old, T value);

    void updateAndInsertAll(List<T> value);

    T find(T value);

    List<T> findAll();

}
