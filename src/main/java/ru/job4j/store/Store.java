package ru.job4j.store;

import ru.job4j.models.Candidate;
import ru.job4j.models.Post;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Store {
    private static final Store INST = new Store();

    private final Map<Integer, Post> posts = new ConcurrentHashMap<>();

    private final Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();

    private Store() {
        posts.put(1, new Post(1, "Junior Java Job"));
        posts.put(2, new Post(2, "Middle Java Job"));
        posts.put(3, new Post(3, "Senior Java Job"));
        candidates.put(1, new Candidate.CandidateBuilder(1, "Junior", "Java").build());
        candidates.put(2, new Candidate.CandidateBuilder(2, "Middle", "Java").build());
        candidates.put(3, new Candidate.CandidateBuilder(3, "Senior", "Java").build());
    }

    public static Store instOf() {
        return INST;
    }

    public Collection<Post> findAllPosts() {
        return posts.values();
    }

    public Collection<Candidate> findAllCandidates() {
        return candidates.values();
    }

    public Collection<Post> findAll() {
        return posts.values();
    }
}
