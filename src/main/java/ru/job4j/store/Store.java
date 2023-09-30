package ru.job4j.store;

import java.util.Collection;

public interface Store<E> {
    Collection<E> findAll();

    void save(E e);

    E findById(int id);

    E findByEmail(String email);

    E findByName(String name);

    boolean delete(int id);
}