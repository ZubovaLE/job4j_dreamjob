package ru.job4j.servlet;

import ru.job4j.models.Candidate;
import ru.job4j.models.Gender;
import ru.job4j.store.DbStore;
import ru.job4j.store.MemStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CandidateServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("candidates", DbStore.instOf().findAllCandidates());
        req.getRequestDispatcher("candidate/candidates.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        MemStore.instOf().save(new Candidate.CandidateBuilder(0, req.getParameter("lastName"),
                req.getParameter("firstName")).withGender(Gender.valueOf(req.getParameter("gender"))).build());
        resp.sendRedirect(req.getContextPath() + "/candidates.do");
    }
}
