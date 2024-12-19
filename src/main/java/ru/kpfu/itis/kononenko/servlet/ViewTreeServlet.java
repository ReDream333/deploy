package ru.kpfu.itis.kononenko.servlet;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.kpfu.itis.kononenko.service.TreeService;

import java.io.IOException;

@WebServlet("/viewTree")
public class ViewTreeServlet extends HttpServlet {

    private TreeService treeService = new TreeService();

    @Override
    public void init(ServletConfig config) throws ServletException {
        treeService = (TreeService) config.getServletContext().getAttribute("treeService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Long treeId = Long.parseLong(req.getParameter("treeId"));

        req.setAttribute("nodes", treeService.convertNodesToJson(treeId).toString());
        req.setAttribute("links", treeService.convertRelationsToJson(treeId).toString());

        req.getRequestDispatcher("view.jsp").forward(req, resp);
    }

}
