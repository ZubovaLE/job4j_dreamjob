package ru.job4j.store;

import ru.job4j.models.Post;

public class MainStore {
    public static void main(String[] args) {
        Store store = DbStore.instOf();
        store.save(new Post(0, "Java Job"));
        for (Post post : store.findAllPosts()) {
            System.out.println(post.getId() + " " + post.getName());
        }
    }
}
