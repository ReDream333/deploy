package ru.kpfu.itis.kononenko.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ru.kpfu.itis.kononenko.entity.User;

import java.io.IOException;

public class BiographyServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long nodeId = Long.parseLong(req.getParameter("nodeId"));
        //дощставть и з бд фото в array а там уже доставать и выводить

        req.setAttribute("nodeId", nodeId);
        req.getRequestDispatcher("biography.jsp").forward(req, resp);
    }
}
