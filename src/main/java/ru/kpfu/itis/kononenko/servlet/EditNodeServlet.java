package ru.kpfu.itis.kononenko.servlet;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.kpfu.itis.kononenko.entity.Node;
import ru.kpfu.itis.kononenko.service.NodeService;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.sql.Date;

@WebServlet("/editNode")
public class EditNodeServlet extends HttpServlet {
    private static final Logger LOG =
            LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private NodeService nodeService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        nodeService = (NodeService) config.getServletContext().getAttribute("nodeService");
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        ObjectMapper mapper = new ObjectMapper();
        JsonNode requestData = mapper.readTree(request.getReader());
        LOG.info("our request data: {}", requestData);

        // Сохраняем обновленные данные в базе данных
        try {
            Long id = requestData.get("key").asLong();
            LOG.info("our request id: {}", id);
            String firstName = requestData.get("firstName").asText().trim();
            LOG.info("firstName: {}", firstName);
            String lastName = requestData.get("lastName").asText().trim();
            LOG.info("lastName: {}", lastName);
            Date birthDate = (requestData.get("birthday").asText().isEmpty()) ? null : Date.valueOf(requestData.get("birthday").asText());
            LOG.info("birthDate: {}", birthDate);
            Date deathDate = (requestData.get("death").asText().isEmpty()) ? null : Date.valueOf(requestData.get("death").asText());
            LOG.info("deathDate: {}", deathDate);
            String photo = requestData.get("photo") == null ? null : requestData.get("photo").asText().trim();
            LOG.info("photo: {}", photo);

            //TODO если фото меняется, то старое надо удалять или заменять. Вроде где то в документации это есть. Плюс нужно, чтобы сохранялось по нужному пресету в папку


            Node updatedNode = nodeService.updateNode(id, firstName, lastName, birthDate, deathDate, photo);
            LOG.info("Our Node is: {}", updatedNode);

            // Отправляем обратно обновленные данные
            response.getWriter().write(mapper.writeValueAsString(updatedNode));

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"Failed to update node.\"}");
        }

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        Long nodeId = Long.valueOf(req.getParameter("nodeId"));

        nodeService.deleteNode(nodeId);
    }
}
