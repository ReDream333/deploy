package ru.kpfu.itis.kononenko.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/*")
public class AuthorizationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        HttpSession session = httpRequest.getSession(false);
        String requestUri = httpRequest.getRequestURI();

        String[] protectedUrls = {
                "/createTree",
                "/viewTree",
                "/profile",
                "/userTrees",
                "/biography",
                "/album",
                "/change"
        };

        if (requestUri.contains("/css/") ||
                requestUri.contains("/js/") ||
                requestUri.contains("/images/") ||
                requestUri.contains("/errors/") ||
                requestUri.contains("/base/")) {
            chain.doFilter(request, response);
            return;
        }

        for (String url : protectedUrls) {
            if (requestUri.startsWith(url)) {
                if (session == null || session.getAttribute("user") == null) {
                    // Перенаправляем на страницу входа, если пользователь не авторизован
                    httpResponse.sendRedirect("/sing");
                    return;
                }
            }
        }

        chain.doFilter(request, response);
    }
}
