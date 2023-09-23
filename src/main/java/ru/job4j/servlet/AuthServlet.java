package ru.job4j.servlet;

import ru.job4j.models.User;
import ru.job4j.store.Store;
import ru.job4j.store.UsqlStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        Store<User> store = UsqlStore.instOf();
        User userFromDB = store.findByEmail(email);
        if (userFromDB != null && userFromDB.getPassword().equals(password)) {
            HttpSession sc = req.getSession();
            sc.setAttribute("user", userFromDB);
            resp.sendRedirect(req.getContextPath() + "/posts.do");
        } else {
            req.setAttribute("error", "Неверный email или пароль");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        }
    }
}
