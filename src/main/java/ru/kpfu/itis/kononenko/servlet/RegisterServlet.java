package ru.kpfu.itis.kononenko.servlet;


import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ru.kpfu.itis.kononenko.entity.User;
import ru.kpfu.itis.kononenko.service.UserService;

import java.io.IOException;
import java.sql.Timestamp;


@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private UserService userService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        userService = (UserService) config.getServletContext().getAttribute("userService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.getRequestDispatcher("registration.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String login = request.getParameter("login");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        response.setContentType("application/json");

        try {
            User user = userService.register(login, email, password, timestamp);
            HttpSession session = request.getSession(true);
            session.setAttribute("user", user);
            response.getWriter().write("{\"success\": true}");
//            response.sendRedirect("profile.jsp");

        }catch (IllegalArgumentException e){
            response.getWriter().write("{\"success\": false, \"errorMessage\": \"" + e.getMessage() + "\"}");
            request.setAttribute("errorMessage", e.getMessage());
        }

    }
}
