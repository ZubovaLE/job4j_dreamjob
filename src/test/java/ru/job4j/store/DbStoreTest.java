package ru.job4j.store;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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
    @DisplayName("When create two posts")
    public void whenCreateTwoPosts() {
        Store store = DbStore.instOf();
        Post postOne = new Post(0, "Java Job");
        Post postTwo = new Post(1, "Java Job2");
        store.save(postOne);
        store.save(postTwo);
        assertThat(store.findAllPosts()).containsAll(List.of(postOne, postTwo));
    }
}