package ru.job4j.servlet;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.job4j.models.Candidate;
import ru.job4j.models.Gender;
import ru.job4j.store.CsqlStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CandidateServletTest {

    @Test
    @DisplayName("When create candidate")
    void doPost() throws ServletException, IOException {
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        when(req.getParameter("id")).thenReturn("0");
        when(req.getParameter("lastName")).thenReturn("lastName");
        when(req.getParameter("firstName")).thenReturn("firstName");
        when(req.getParameter("gender")).thenReturn(Gender.MALE.toString());
        new CandidateServlet().doPost(req, resp);
        Candidate candidate = CsqlStore.instOf().findById(0);
        assertNotNull(candidate);
    }
}