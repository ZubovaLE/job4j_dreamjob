package ru.job4j.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class DownloadServlet extends HttpServlet {
    private final static String REQUIRED_FILE_NAME = "users.txt";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        File users = null;
        for (File file : new File("c:\\images\\").listFiles()) {
            if (REQUIRED_FILE_NAME.equals(file.getName())) {
                users = file;
                break;
            }
        }
        try (FileInputStream stream = new FileInputStream(users)) {
            resp.setContentType("application/octet-stream");
            resp.setHeader("Content-Disposition", "attachment; filename=\"" + users.getName() + "\"");
            resp.getOutputStream().write(stream.readAllBytes());
        }
    }
}
