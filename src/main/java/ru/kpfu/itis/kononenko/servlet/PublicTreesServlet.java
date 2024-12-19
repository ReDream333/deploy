package ru.kpfu.itis.kononenko.servlet;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.kpfu.itis.kononenko.entity.Tree;
import ru.kpfu.itis.kononenko.service.TreeService;

import java.io.IOException;
import java.util.List;


@WebServlet("/publicTrees")
public class PublicTreesServlet extends HttpServlet {

    private TreeService treeService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        treeService = (TreeService) config.getServletContext().getAttribute("treeService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<Tree> publicTrees = treeService.getPublicTrees();

        req.setAttribute("publicTrees", publicTrees);
        req.getRequestDispatcher("/publicTrees.jsp").forward(req, resp);
    }
}
