//package ru.kpfu.itis.kononenko.filter;
//
//import jakarta.servlet.*;
//import jakarta.servlet.annotation.WebFilter;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//import java.io.IOException;
//
//@WebFilter("/*")
//public class AuthorizationFilter implements Filter {
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//        HttpServletRequest httpRequest = (HttpServletRequest) request;
//        HttpServletResponse httpResponse = (HttpServletResponse) response;
//
//        HttpSession session = httpRequest.getSession(false);
//
//        // URL, которые не требуют авторизации (например, логин, регистрация)
//        String[] publicUrls = {"/sing", "/register", "/home.jsp", "/faq.html"};
//
//        String requestUri = httpRequest.getRequestURI();
//        for (String url : publicUrls) {
//            if (requestUri.startsWith(url)) {
//                chain.doFilter(request, response);
//                return;
//            }
//        }
//
//
//        if (session == null || session.getAttribute("user") == null) {
//            httpResponse.sendRedirect("/sing");
//            return;
//        }
//
//        chain.doFilter(request, response);
//    }
//
//}
