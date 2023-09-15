package ru.job4j.store;

import ru.job4j.models.Candidate;
import ru.job4j.models.Post;

import java.util.Collection;

public interface Store {
    Collection<Post> findAllPosts();

    Collection<Candidate> findAllCandidates();

    void save(Post post);

    void save(Candidate candidate);

    Post findPostById(int id);

    Candidate findCandidateById(int id);

    boolean delete (int id);
}
