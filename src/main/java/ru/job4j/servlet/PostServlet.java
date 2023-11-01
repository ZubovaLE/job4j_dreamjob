package ru.job4j.servlet;

import ru.job4j.models.Post;
import ru.job4j.store.PsqlStore;
import ru.job4j.store.Store;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PostServlet extends HttpServlet {

    private final Store<Post> store = PsqlStore.instOf();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("user", req.getSession().getAttribute("user"));
        req.setAttribute("posts", store.findAll());
        req.getRequestDispatcher("post/posts.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        int id = Integer.parseInt(req.getParameter("id"));
        if (Boolean.parseBoolean(req.getParameter("isDeleted"))) {
            store.delete(id);
        } else {
            store.save(
                    new Post(
                            Integer.parseInt(req.getParameter("id")),
                            req.getParameter("name")
                    )
            );
        }
        resp.sendRedirect(req.getContextPath() + "/posts.do");
    }
}