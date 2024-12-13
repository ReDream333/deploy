package ru.kpfu.itis.kononenko.servlet;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.kpfu.itis.kononenko.entity.Node;
import ru.kpfu.itis.kononenko.service.FamilyService;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.sql.Date;

@WebServlet("/addFamilyMember")
public class AddFamilyMemberServlet extends HttpServlet {

    private static final Logger LOG =
            LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final FamilyService familyService = new FamilyService();
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
            ObjectMapper mapper = new ObjectMapper();

        JsonNode requestData = mapper.readTree(request.getReader());
        LOG.info("our request data: {}", requestData);


        Node newMemberNode = new Node(
                null,
                requestData.get("treeId").asLong(),
                requestData.get("firstName").asText(),
                requestData.get("lastName").asText(),
                requestData.has("surname") ? requestData.get("surname").asText() : null,
                requestData.get("gender").asText().charAt(0),
                requestData.get("birthDate").isNull() ? null: Date.valueOf(requestData.get("birthDate").asText()),
                requestData.get("deathDate").isNull() ? null: Date.valueOf(requestData.get("deathDate").asText()),
                requestData.get("biography").isNull() ? null: requestData.get("biography").asText(),
                requestData.has("photo") ? requestData.get("photo").asText() : null
        );

        LOG.info("Have this: {}", newMemberNode);

        try {
            LOG.info("Try save this");
            Node savedMember = familyService.addNewNode(newMemberNode, requestData.get("childId").asLong());
            LOG.info("YEEES Save! - {}", savedMember);

            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write(mapper.writeValueAsString(savedMember));
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\":\"Failed to add new family member.\"}");
        }
    }


}
