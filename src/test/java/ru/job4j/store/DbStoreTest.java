package ru.job4j.store;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.job4j.models.Candidate;
import ru.job4j.models.Gender;
import ru.job4j.models.Post;

import static org.assertj.core.api.Assertions.*;

import java.util.List;


class DbStoreTest {
    @Test
    @DisplayName("When create post")
    public void whenCreatePost() {
        Store store = DbStore.instOf();
        Post post = new Post(0, "Java Job");
        store.save(post);
        Post postInDb = store.findPostById(post.getId());
        assertThat(postInDb.getName()).isEqualTo(post.getName());
    }

    @Test
    @DisplayName("When update post")
    public void whenUpdatePost() {
        Store store = DbStore.instOf();
        Post post = new Post(0, "Java Job");
        store.save(post);
        String newName = "Junior";
        post.setName(newName);
        store.save(post);
        Post postInDb = store.findPostById(post.getId());
        assertThat(postInDb.getName()).isEqualTo(newName);
    }

    @Test
    @DisplayName("When findAllPosts")
    public void whenFindAllPosts() {
        Store store = DbStore.instOf();
        Post postOne = new Post(0, "Java Job");
        Post postTwo = new Post(1, "Java Job2");
        store.save(postOne);
        store.save(postTwo);
        assertThat(store.findAllPosts()).containsAll(List.of(postOne, postTwo));
    }

    @Test
    @DisplayName("When create candidate")
    public void whenCreateCandidate() {
        Store store = DbStore.instOf();
        Candidate candidate = new Candidate.CandidateBuilder(0, "Jackson", "James")
                .withGender(Gender.MALE).build();
        store.save(candidate);
        Candidate candidateInDb = store.findCandidateById(candidate.getId());
        assertThat(candidateInDb.getLastName()).isEqualTo(candidate.getLastName());
    }

    @Test
    @DisplayName("When findAllCandidates")
    public void whenFindAllCandidates() {
        Store store = DbStore.instOf();
        Candidate candidateOne = new Candidate.CandidateBuilder(0, "Jackson", "James")
                .withGender(Gender.MALE).build();
        Candidate candidateTwo = new Candidate.CandidateBuilder(0, "Clark", "Katy")
                .withGender(Gender.FEMALE).build();
        store.save(candidateOne);
        store.save(candidateTwo);
        assertThat(store.findAllCandidates()).containsAll(List.of(candidateOne, candidateTwo));
    }
}