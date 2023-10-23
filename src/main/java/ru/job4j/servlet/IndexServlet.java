package ru.job4j.servlet;

import ru.job4j.models.Post;
import ru.job4j.store.PsqlStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class IndexServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Post> todayPosts = PsqlStore.instOf().findAll().stream().filter(post -> post.getCreated().isAfter(LocalDate.now())).collect(Collectors.toList());
        req.getRequestDispatcher("index.jsp").forward(req, resp);
    }
}
