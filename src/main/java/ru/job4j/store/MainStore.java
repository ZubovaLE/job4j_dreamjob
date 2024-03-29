package ru.job4j.store;

import ru.job4j.models.Post;

import java.time.LocalDateTime;

public class MainStore {
    public static void main(String[] args) {
        Store<Post> store = PsqlStore.instOf();
        store.save(new Post(0, "Java Job", LocalDateTime.now()));
        for (Post post : store.findAll()) {
            System.out.println(post.getId() + " " + post.getName());
        }
    }
}