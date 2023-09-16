package ru.job4j.store;

import ru.job4j.models.Candidate;
import ru.job4j.models.Post;

import java.util.Collection;

public interface Store<E> {
    Collection<E> findAll();

    void save(E e);

    E findById(int id);

    boolean delete (int id);
}
