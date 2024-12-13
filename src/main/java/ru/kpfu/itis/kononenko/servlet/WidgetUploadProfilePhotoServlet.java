package ru.kpfu.itis.kononenko.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.kpfu.itis.kononenko.entity.User;
import ru.kpfu.itis.kononenko.service.UserService;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.stream.Collectors;

@WebServlet("/saveProfilePhoto")
public class WidgetUploadProfilePhotoServlet extends HttpServlet {
    private static final Logger LOG =
            LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String requestBody = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        JSONObject jsonObject = new JSONObject(requestBody);

        String photoUrl = jsonObject.getString("imageUrl");

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Long userId = user.id();

        UserService userService = new UserService();
        try {
            session.setAttribute("user", userService.photoUpload(userId, photoUrl));

            response.setContentType("application/json");
            response.getWriter().write("{\"success\": true, \"message\": \"Фото профиля обновлено.\"}");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"success\": false, \"message\": \"Ошибка сервера.\"}");
            e.printStackTrace();
        }
    }
}

