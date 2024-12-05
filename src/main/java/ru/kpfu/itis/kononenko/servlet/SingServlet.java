package ru.kpfu.itis.kononenko.servlet;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ru.kpfu.itis.kononenko.entity.User;
import ru.kpfu.itis.kononenko.service.UserService;

import java.io.IOException;

@WebServlet("/sing")
public class SingServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.getRequestDispatcher("sing.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        UserService userService = new UserService();

        User user = userService.checkSign(login, password);
        if (user!=null) {
            // Создание сессии для пользователя
            HttpSession session = request.getSession();
            session.setAttribute("user", user); // Сохраняем имя пользователя в сессии
            response.sendRedirect("profile.jsp");
        } else {
            response.sendRedirect("registration.jsp");
        }

    }
}
