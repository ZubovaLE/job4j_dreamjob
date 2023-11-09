package ru.job4j.servlet;

import ru.job4j.models.Candidate;
import ru.job4j.models.Gender;
import ru.job4j.store.CsqlStore;
import ru.job4j.store.Store;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

public class CandidateServlet extends HttpServlet {

    private final Store<Candidate> store = CsqlStore.instOf();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("candidates", store.findAll());
        req.setAttribute("user", req.getSession().getAttribute("user"));
        req.getRequestDispatcher("candidate/candidates.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        int id = Integer.parseInt(req.getParameter("id"));
        String photo = req.getParameter("photo");
        boolean isDeleted = Boolean.parseBoolean(req.getParameter("isDeleted"));
        if (isDeleted) {
            isDeleted = photo != null && new File("c:\\images\\" + photo).delete();
            store.delete(id);
        } else {
            Candidate candidate = new Candidate.CandidateBuilder(
                    id,
                    req.getParameter("lastName"),
                    req.getParameter("firstName")
            ).withGender(Gender.valueOf(req.getParameter("gender"))).build();
            if (photo != null) {
                candidate.setPhoto(photo);
            }
            store.save(candidate);
        }
        resp.sendRedirect(req.getContextPath() + "/candidates.do");
    }
}