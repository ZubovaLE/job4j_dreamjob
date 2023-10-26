package ru.job4j.store;

import java.util.Collection;

public interface Store<E> {
    Collection<E> findAll();

    E findById(int id);

    void save(E e);

    boolean delete(int id);
}