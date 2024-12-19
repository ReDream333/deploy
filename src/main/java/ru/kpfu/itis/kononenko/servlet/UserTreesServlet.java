package ru.kpfu.itis.kononenko.servlet;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.kpfu.itis.kononenko.entity.Tree;
import ru.kpfu.itis.kononenko.entity.User;
import ru.kpfu.itis.kononenko.service.TreeService;

import java.io.IOException;
import java.util.List;

@WebServlet("/userTrees")
public class UserTreesServlet extends HttpServlet {
    private TreeService treeService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        treeService = (TreeService) config.getServletContext().getAttribute("treeService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        // Получение пользователя из сессии
        User user = (User) session.getAttribute("user");
        Long userId = user.id();

        // Получение списка деревьев пользователя
        List<Tree> userTrees = treeService.getTreesByUserId(userId);

        // Установка списка деревьев как атрибута запроса
        request.setAttribute("userTrees", userTrees);

        // Перенаправление на JSP-страницу для отображения деревьев
        request.getRequestDispatcher("user-trees.jsp").forward(request, response);
    }
}
