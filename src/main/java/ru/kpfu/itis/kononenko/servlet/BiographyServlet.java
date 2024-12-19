package ru.kpfu.itis.kononenko.servlet;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.kpfu.itis.kononenko.service.NodeBiographyService;
import ru.kpfu.itis.kononenko.service.NodeService;

import java.io.IOException;

@WebServlet("/biography")
public class BiographyServlet extends HttpServlet {

    private NodeService nodeService;
    private NodeBiographyService biographyService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        nodeService = (NodeService) config.getServletContext().getAttribute("nodeService");
        biographyService = (NodeBiographyService) config.getServletContext().getAttribute("nodeBiographyService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long nodeId = Long.parseLong(req.getParameter("nodeId"));
        String nodeName = nodeService.getNodeName(nodeId);
        String biographyText = biographyService.getBiography(nodeId);

        req.setAttribute("nodeName", nodeName);
        req.setAttribute("nodeId", nodeId);
        req.setAttribute("biography", biographyText);

        req.getRequestDispatcher("biography.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Long nodeId = Long.valueOf(req.getParameter("nodeId"));
        String biography = req.getParameter("biography");

        biographyService.saveBiography(nodeId, biography);

        resp.sendRedirect("biography?nodeId=" + nodeId);

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        Long nodeId = Long.valueOf(req.getParameter("nodeId"));
        biographyService.deleteBiography(nodeId);

    }
}
