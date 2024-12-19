package ru.kpfu.itis.kononenko.servlet;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ru.kpfu.itis.kononenko.entity.Node;
import ru.kpfu.itis.kononenko.service.FamilyService;

import java.io.IOException;
import java.sql.Date;

@WebServlet("/addInitialNode")
public class AddInitialNodeServlet extends HttpServlet {

    private FamilyService familyService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        familyService = (FamilyService) config.getServletContext().getAttribute("familyService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("add-node.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();

        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        char gender = req.getParameter("gender").charAt(0);
        Date birthDate = parseDate(req.getParameter("birthDate"));
        Date deathDate = parseDate(req.getParameter("deathDate"));
        String comment = req.getParameter("comment");

        Long treeId = (Long) session.getAttribute("currentTreeId");

        Node selfNode = new Node(null, treeId, firstName, lastName, gender, birthDate, deathDate, comment, null);

        familyService.addInitialNode(selfNode);

        resp.sendRedirect(req.getContextPath() + "/viewTree?treeId=" + treeId + "&success=true");

    }

    private Date parseDate(String dateString) {
        try {
            return Date.valueOf(dateString);
        } catch (Exception e) {
            return null;
        }
    }
}

