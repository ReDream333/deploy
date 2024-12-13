package ru.kpfu.itis.kononenko.servlet;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.kpfu.itis.kononenko.entity.User;
import ru.kpfu.itis.kononenko.service.UserService;

import java.io.IOException;
import java.lang.invoke.MethodHandles;

@WebServlet("/sing")
public class SingServlet extends HttpServlet {
    private static final Logger LOG =
            LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.getRequestDispatcher("sing.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        User user = userService.checkSign(login, password);
        if (user!=null) {
            // Создание сессии для пользователя
            HttpSession session = request.getSession();
            LOG.info("We put user in session is {}", user);
            session.setAttribute("user", user); // Сохраняем имя пользователя в сессии
            response.sendRedirect("profile.jsp");
        } else {
            response.sendRedirect("registration.jsp");
        }

    }
}
