//package ru.kpfu.itis.kononenko.filter;
//
//import jakarta.servlet.*;
//import jakarta.servlet.annotation.WebFilter;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//import ru.kpfu.itis.kononenko.entity.User;
//import ru.kpfu.itis.kononenko.service.TreeService;
//
//import java.io.IOException;
//
//@WebFilter("/viewTree")
//public class TreeIdFilter implements Filter {
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//        HttpServletRequest httpRequest = (HttpServletRequest) request;
//        HttpServletResponse httpResponse = (HttpServletResponse) response;
//
//        HttpSession session = httpRequest.getSession(false);
//
//        User user = (User) session.getAttribute("user");
//        Long treeId = (Long) session.getAttribute("currentTreeId");
//
//
//        if (!new TreeService().checkUserIdForThisTree(user.id(), treeId)) {
//            httpResponse.sendRedirect("exception.html");
//        }
//
//        chain.doFilter(request, response);
//
//    }
//
//}
