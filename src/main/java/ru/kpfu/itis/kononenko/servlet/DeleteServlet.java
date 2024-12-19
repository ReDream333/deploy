package ru.kpfu.itis.kononenko.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.kpfu.itis.kononenko.entity.User;
import ru.kpfu.itis.kononenko.service.TreeService;
import ru.kpfu.itis.kononenko.service.UserService;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/delete")
public class DeleteServlet extends HttpServlet {
    private static final Logger LOG =
            LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private UserService userService;
    private TreeService treeService;
    private ObjectMapper mapper;

    @Override
    public void init(ServletConfig config) throws ServletException {
        userService = (UserService) config.getServletContext().getAttribute("userService");
        mapper = (ObjectMapper) config.getServletContext().getAttribute("objectMapper");
        treeService = (TreeService) config.getServletContext().getAttribute("treeService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        userService.deleteUser(user.id());
        LOG.info("Deleted user {}", user.id());
        req.getRequestDispatcher("/logout").forward(req, resp);
    }

    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Map<String, String> jsonResponse = new HashMap<>();
        HttpSession session = request.getSession(false);

        if (session != null && session.getAttribute("user") != null) {

            User user = (User) session.getAttribute("user");
            Long userId = user.id();

            boolean isDeleted = userService.deleteUser(userId);

            if (isDeleted) {
                treeService.deleteAllTreeByUser(userId);

                LOG.info("Пользователь {} удалил свой аккаунт.", user.username());
                session.invalidate();
                jsonResponse.put("status", "success");
                jsonResponse.put("message", "Аккаунт успешно удалён.");
            } else {
                LOG.error("Ошибка при удалении аккаунта пользователя {}", user.username());
                jsonResponse.put("status", "error");
                jsonResponse.put("message", "Не удалось удалить аккаунт.");
            }
        } else {
            LOG.warn("Попытка удалить аккаунт без аутентификации.");
            jsonResponse.put("status", "error");
            jsonResponse.put("message", "Пользователь не аутентифицирован.");
        }

        mapper.writeValue(response.getWriter(), jsonResponse);
    }
}
