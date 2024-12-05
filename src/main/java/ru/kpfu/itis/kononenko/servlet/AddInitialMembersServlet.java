package ru.kpfu.itis.kononenko.servlet;

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

@WebServlet("/addInitialMembers")
public class AddInitialMembersServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String stage = (String) session.getAttribute("stage");

        if (stage == null) stage = "self";

        //определяем заголовок
        String heading = switch (stage) {
            case "self" -> "Введите свои данные";
            case "mother" -> "Введите данные матери";
            case "father" -> "Введите данные отца";
            default -> "";
        };

        req.setAttribute("stage", stage);
        req.setAttribute("heading", heading);

        req.getRequestDispatcher("add-node.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        String stage = req.getParameter("stage");

        switch (stage) {
            case "self":
                readNode(req, session, "selfNode");
                session.setAttribute("stage", "mother");
                resp.sendRedirect("/addInitialMembers");
                break;
            case "mother":
                readNode(req, session, "motherNode");
                session.setAttribute("stage", "father");
                resp.sendRedirect("/addInitialMembers");
                break;
            case "father":
                readNode(req, session, "fatherNode");
                finalizeNodes(session);
                resp.sendRedirect("/viewTree?treeId=" + session.getAttribute("treeId"));
                break;
        }


    }

    private void readNode(HttpServletRequest req, HttpSession session, String attributeName) {

        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String surname = req.getParameter("surname");
        char gender = req.getParameter("gender").charAt(0);
        Date birthDate = parseDate(req.getParameter("birthDate"));
        Date deathDate = parseDate(req.getParameter("deathDate"));
        String biography = req.getParameter("biography");

        Long treeId = (Long) session.getAttribute("currentTreeId");

        // Сохраняем данные ноды в сессии
        Node node = new Node(null, treeId, firstName, lastName, surname, gender, birthDate, deathDate, biography);
        session.setAttribute(attributeName, node);
    }

    private void finalizeNodes(HttpSession session) {
        Long treeId = (Long) session.getAttribute("currentTreeId");
        Node selfNode = (Node) session.getAttribute("selfNode");
        Node parent1Node = (Node) session.getAttribute("motherNode");
        Node parent2Node = (Node) session.getAttribute("fatherNode");

        // Сохраняем ноды в БД
        FamilyService familyService = new FamilyService();
        familyService.addInitialNodes(treeId, selfNode, parent1Node, parent2Node);

        // Очищаем сессию
        session.removeAttribute("selfNode");
        session.removeAttribute("motherNode");
        session.removeAttribute("fatherNode");
        session.removeAttribute("stage");
    }

    private Date parseDate(String dateString) {
        try {
            return Date.valueOf(dateString);
        } catch (Exception e) {
            return null;
        }
    }
}

