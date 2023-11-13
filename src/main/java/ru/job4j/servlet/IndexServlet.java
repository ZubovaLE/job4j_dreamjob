package ru.job4j.servlet;

import ru.job4j.store.CsqlStore;
import ru.job4j.store.PsqlStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class IndexServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("todayPosts", PsqlStore.instOf().findTodayPosts());
        req.setAttribute("todayCandidates", CsqlStore.instOf().findTodayCandidates());
        req.getRequestDispatcher("index.jsp").forward(req, resp);
    }
}