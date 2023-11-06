package ru.job4j.store;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.job4j.models.Post;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

class PsqlStoreTest {
    @Test
    @DisplayName("When create post")
    public void whenCreatePost() {
        Store store = PsqlStore.instOf();
        Post post = new Post(0, "Java Job", LocalDateTime.now());
        store.save(post);
        Post postInDb = (Post) store.findById(post.getId());
        assertThat(postInDb.getName()).isEqualTo(post.getName());
    }

    @Test
    @DisplayName("When update post")
    public void whenUpdatePost() {
        Store store = PsqlStore.instOf();
        Post post = new Post(0, "Java Job", LocalDateTime.of(2000, 1, 1, 12, 0));
        store.save(post);
        String newName = "Junior";
        post.setName(newName);
        store.save(post);
        Post postInDb = (Post) store.findById(post.getId());
        assertThat(postInDb.getName()).isEqualTo(newName);
    }

    @Test
    @DisplayName("When findAllPosts")
    public void whenFindAllPosts() {
        Store store = PsqlStore.instOf();
        Post postOne = new Post(0, "Java Job", LocalDateTime.of(2000, 1, 1, 12, 0));
        Post postTwo = new Post(1, "Java Job2", LocalDateTime.of(2000, 8, 1, 12, 0));
        store.save(postOne);
        store.save(postTwo);
        assertThat(store.findAll()).containsAll(List.of(postOne, postTwo));
    }
}