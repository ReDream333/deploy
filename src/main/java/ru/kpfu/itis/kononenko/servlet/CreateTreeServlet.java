package ru.kpfu.itis.kononenko.servlet;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ru.kpfu.itis.kononenko.entity.User;
import ru.kpfu.itis.kononenko.service.TreeService;

import java.io.IOException;
import java.sql.Timestamp;

@WebServlet("/createTree")
public class CreateTreeServlet extends HttpServlet {

    private TreeService treeService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        treeService = (TreeService) config.getServletContext().getAttribute("treeService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.getRequestDispatcher("tree-form.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("user");

        String treeName = request.getParameter("treeName");
        boolean isPrivate = Boolean.parseBoolean(request.getParameter("isPrivate"));
        Timestamp createAt = new Timestamp(System.currentTimeMillis());

        long treeId = treeService.createTree(currentUser.id(), treeName, isPrivate, createAt);

        session.setAttribute("currentTreeId", treeId);
        response.sendRedirect("/addInitialNode");
    }
}

