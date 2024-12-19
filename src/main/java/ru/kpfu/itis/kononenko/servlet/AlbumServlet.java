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
import ru.kpfu.itis.kononenko.entity.NodePhoto;
import ru.kpfu.itis.kononenko.service.NodePhotoService;
import ru.kpfu.itis.kononenko.service.NodeService;

import java.io.IOException;
import java.lang.invoke.MethodHandles;

@WebServlet("/album")
public class AlbumServlet extends HttpServlet {
    private static final Logger LOG =
            LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private NodeService nodeService;
    private NodePhotoService photoService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        nodeService = (NodeService) config.getServletContext().getAttribute("nodeService");
        photoService = (NodePhotoService) config.getServletContext().getAttribute("nodePhotoService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long nodeId = Long.parseLong(req.getParameter("nodeId"));

        req.setAttribute("photos", photoService.convertPhotosToJson(nodeId).toString());
        req.setAttribute("nodeName", nodeService.getNodeName(nodeId));

        req.getRequestDispatcher("photo-album.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("application/json");
        ObjectMapper mapper = new ObjectMapper();
        JsonNode requestData = mapper.readTree(req.getReader());
        LOG.info("our request data: {}", requestData);

        NodePhoto newPhoto = photoService.addPhoto(
                requestData.get("nodeId").asLong(),
                requestData.get("photoUrl").asText(),
                requestData.get("description").asText()
        );

        LOG.info("Our Node is: {}", newPhoto);
        resp.getWriter().write(mapper.writeValueAsString(newPhoto));
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode requestData = mapper.readTree(req.getReader());
        LOG.info("our request data: {}", requestData);

        // Сохраняем обновленные данные в базе данных
        try {

            Long id = requestData.get("id").asLong();
            String description = requestData.get("description").asText().trim();

            NodePhoto updatedNodePhoto = photoService.updatePhotoDescription(id, description);
            LOG.info("Our NodePhoto is: {}", updatedNodePhoto);

            // Отправляем обратно обновленные данные
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write("{\"message\": \"Описание обновлено успешно\"}");


        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"error\": \"Failed to update node.\"}");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        Long photoId = Long.valueOf(req.getParameter("id"));
        photoService.deletePhoto(photoId);
    }
}
