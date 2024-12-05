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

@WebServlet("/change")
public class ChangeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();


        //TODO скорее всего это лишнее
        User user = (User) session.getAttribute("user");
        request.setAttribute("user", user);

        request.getRequestDispatcher("update.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain"); // Указываем тип ответа как текст
        resp.setCharacterEncoding("UTF-8");

        String newName = req.getParameter("newName");

        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        if (user != null && newName != null && !newName.trim().isEmpty()) {
            UserService userService = new UserService();
            User chagedUser = userService.changeName(newName, user.username());
            session.setAttribute("user", chagedUser);
            resp.getWriter().write("success");
        } else {
            resp.getWriter().write("Некорректное имя пользователя!");
        }
    }
}
