package ru.job4j.servlet;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.job4j.models.User;
import ru.job4j.store.UsqlStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RegServletTest {

    @Test
    @DisplayName("Create user")
    void whenCreateUser() throws ServletException, IOException {
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        when(req.getParameter("email")).thenReturn("email");
        when(req.getParameter("password")).thenReturn("password");
        new RegServlet().doPost(req, resp);
        User user = UsqlStore.instOf().findByEmail("email");
        assertNotNull(user);
    }
}