package ru.kpfu.itis.kononenko.servlet;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ru.kpfu.itis.kononenko.dao.UserDao;
import ru.kpfu.itis.kononenko.entity.User;
import ru.kpfu.itis.kononenko.service.UserService;
import ru.kpfu.itis.kononenko.util.Configuration;
import ru.kpfu.itis.kononenko.util.PasswordUtil;

import java.io.IOException;

@WebServlet("/SingServlet")
public class SingServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        UserService userService = new UserService();

        if (userService.checkSing(login, password)) {
            // Создание сессии для пользователя
            HttpSession session = request.getSession();
            session.setAttribute("login", login); // Сохраняем имя пользователя в сессии

            // Перенаправление на страницу приветствия
            response.sendRedirect("profile.jsp");
        } else {
          //зарегайся, дурак
            response.sendRedirect("registration.jsp");
        }

    }
}
