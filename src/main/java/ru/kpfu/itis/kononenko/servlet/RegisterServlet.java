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

import java.io.IOException;


@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        UserService userService = new UserService();

        if (userService.register(login, email, password)) {
            // Создание сессии для пользователя
            HttpSession session = request.getSession();
            session.setAttribute("login", login); // Сохраняем имя пользователя в сессии

            // Перенаправление на страницу приветствия
            response.sendRedirect("profile.jsp");
        } else {
            //TODO надо сделать страничку какую то сто ли
            response.sendRedirect("register.jsp?error=registration_failed");
        }

        //TODO надо сделать Закрытие соединения

    }
}
