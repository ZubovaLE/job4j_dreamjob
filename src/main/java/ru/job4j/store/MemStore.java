package ru.job4j.store;

import ru.job4j.models.Candidate;
import ru.job4j.models.Gender;
import ru.job4j.models.Post;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MemStore {
    private static AtomicInteger POST_ID = new AtomicInteger(4);
    private static AtomicInteger CANDIDATE_ID = new AtomicInteger(2);

    private static final MemStore INST = new MemStore();

    private final Map<Integer, Post> posts = new ConcurrentHashMap<>();

    private final Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();

    private MemStore() {
        posts.put(1, new Post(1, "Junior Java Job", LocalDateTime.now()));
        posts.put(2, new Post(2, "Middle Java Job", LocalDateTime.now().plusMonths(1)));
        posts.put(3, new Post(3, "Senior Java Job", LocalDateTime.now().plusMonths(2)));
        candidates.put(1, new Candidate.CandidateBuilder(1, "Depp", "Max",
                LocalDateTime.of(2010, 8, 1, 1, 1))
                .withGender(Gender.MALE)
                .build());
        candidates.put(2, new Candidate.CandidateBuilder(2, "Green", "Anna",
                LocalDateTime.of(2010, 8, 1, 1, 2))
                .withGender(Gender.FEMALE)
                .build());
        candidates.put(3, new Candidate.CandidateBuilder(3, "Marlow", "Java",
                LocalDateTime.of(2010, 8, 1, 1, 3))
                .withGender(Gender.MALE)
                .build());
    }

    public static MemStore instOf() {
        return INST;
    }

    public Collection<Post> findAllPosts() {
        return posts.values();
    }

    public Collection<Candidate> findAllCandidates() {
        return candidates.values();
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
