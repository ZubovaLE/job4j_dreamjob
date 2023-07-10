package ru.job4j.store;

import ru.job4j.models.Candidate;
import ru.job4j.models.Gender;
import ru.job4j.models.Post;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class Store {
    private static AtomicInteger POST_ID = new AtomicInteger(4);
    private static AtomicInteger CANDIDATE_ID = new AtomicInteger(2);

    private static final Store INST = new Store();

    private final Map<Integer, Post> posts = new ConcurrentHashMap<>();

    private final Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();

    private Store() {
        posts.put(1, new Post(1, "Junior Java Job"));
        posts.put(2, new Post(2, "Middle Java Job"));
        posts.put(3, new Post(3, "Senior Java Job"));
        candidates.put(1, new Candidate.CandidateBuilder(1, "Depp", "Max")
                .withGender(Gender.MALE)
                .build());
        candidates.put(2, new Candidate.CandidateBuilder(2, "Green", "Anna")
                .withGender(Gender.FEMALE)
                .build());
        candidates.put(3, new Candidate.CandidateBuilder(3, "Marlow", "Java")
                .withGender(Gender.MALE)
                .build());
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

    public void save(Candidate candidate) {
        candidate.setId(POST_ID.incrementAndGet());
        candidates.put(candidate.getId(), candidate);
    }

    public void save(Post post) {
        if (post.getId() == 0) {
            post.setId(POST_ID.incrementAndGet());
        }
        posts.put(post.getId(), post);
    }

    public Post findPostById(int id) {
        return posts.get(id);
    }

    public Candidate findCandidateById(int id) {
        return candidates.get(id);
    }
}
