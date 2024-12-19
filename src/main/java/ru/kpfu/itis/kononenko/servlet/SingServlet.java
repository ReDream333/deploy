package ru.kpfu.itis.kononenko.servlet;


import com.fasterxml.jackson.databind.JsonNode;
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
import ru.kpfu.itis.kononenko.service.UserService;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/sing")
public class SingServlet extends HttpServlet {
    private static final Logger LOG =
            LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private UserService userService;
    private ObjectMapper mapper;

    @Override
    public void init(ServletConfig config) throws ServletException {
        userService = (UserService) config.getServletContext().getAttribute("userService");
        mapper = (ObjectMapper) config.getServletContext().getAttribute("objectMapper");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.getRequestDispatcher("sing.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        JsonNode requestData = mapper.readTree(request.getReader());
        String login = requestData.get("login").asText();
        String password = requestData.get("password").asText();

        User user = userService.checkSign(login, password);
        Map<String, Object> responseData = new HashMap<>();


        if (user!=null) {
            HttpSession session = request.getSession();
            session.setAttribute("user", user); // Сохраняем имя пользователя в сессии
            LOG.info("We put user in session is {}", user);

            responseData.put("status", "success");
            responseData.put("redirectUrl", "profile.jsp");
        } else {

            LOG.warn("Invalid login or password: {} or {}", login, password);
            responseData.put("status", "error");
            responseData.put("message", "Неправильный логин или пароль");
        }

        mapper.writeValue(response.getWriter(), responseData);

    }
}
