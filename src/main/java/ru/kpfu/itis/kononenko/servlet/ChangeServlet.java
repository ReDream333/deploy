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

@WebServlet("/change")
public class ChangeServlet extends HttpServlet {

    private UserService userService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        userService = (UserService) config.getServletContext().getAttribute("userService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("update.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        String currentName = req.getParameter("currentName");
        String currentPassword = req.getParameter("currentPassword");
        String newName = req.getParameter("newName");
        String newPassword = req.getParameter("newPassword");

        if (currentName != null && currentPassword != null) {
            if (user.username().equals(currentName) && userService.validatePassword(user, currentPassword)) {
                resp.getWriter().write("success");
            } else {
                resp.getWriter().write("error");
            }
        } else if (newName != null || newPassword != null) {

            if (newName != null && !newName.isEmpty()) {
                User chagedUser = userService.changeName(newName, user.id());
                session.setAttribute("user", chagedUser);
            }
            if (newPassword != null && !newPassword.isEmpty()) {
                User chagedUser = userService.changePassword(newPassword, user.id());
                session.setAttribute("user", chagedUser);
            }
            resp.getWriter().write("success");
        }
    }

}
