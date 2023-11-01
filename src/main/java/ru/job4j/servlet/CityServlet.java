package ru.job4j.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.job4j.models.City;
import ru.job4j.store.CityStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class CityServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json; charset=utf-8");
        List<City> cities = (List<City>) CityStore.instOf().findAll();
        ObjectMapper mapper = new ObjectMapper();
        String citiesString = mapper.writeValueAsString(cities);
        resp.getWriter().write(citiesString);
    }
}
