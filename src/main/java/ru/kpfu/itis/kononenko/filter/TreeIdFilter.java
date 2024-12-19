package ru.kpfu.itis.kononenko.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ru.kpfu.itis.kononenko.entity.Tree;
import ru.kpfu.itis.kononenko.entity.User;
import ru.kpfu.itis.kononenko.service.TreeService;

import java.io.IOException;
import java.util.Objects;

@WebFilter("/viewTree")
public class TreeIdFilter implements Filter {

    TreeService treeService = new TreeService();


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        treeService = (TreeService) filterConfig.getServletContext().getAttribute("treeService");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        HttpSession session = httpRequest.getSession(false);

        User user = (User) session.getAttribute("user");
        Long treeId = Long.valueOf(httpRequest.getParameter("treeId"));

        Tree tree = treeService.findById(treeId);


        if (tree.isPrivate() && !Objects.equals(tree.userId(), user.id())) {
            httpResponse.sendRedirect("/error.jsp");
        }

        chain.doFilter(request, response);

    }

}
