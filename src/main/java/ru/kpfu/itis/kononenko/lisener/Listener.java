package ru.kpfu.itis.kononenko.lisener;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import ru.kpfu.itis.kononenko.service.*;

@WebListener
public class Listener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        final UserService userService = new UserService();
        sce.getServletContext().setAttribute("userService", userService);

        final TreeService treeService = new TreeService();
        sce.getServletContext().setAttribute("treeService", treeService);

        final NodeService nodeService = new NodeService();
        sce.getServletContext().setAttribute("nodeService", nodeService);

        final FamilyService familyService = new FamilyService();
        sce.getServletContext().setAttribute("familyService", familyService);

        final NodeBiographyService nodeBiographyService = new NodeBiographyService();
        sce.getServletContext().setAttribute("nodeBiographyService", nodeBiographyService);

        final NodePhotoService nodePhotoService = new NodePhotoService();
        sce.getServletContext().setAttribute("nodePhotoService", nodePhotoService);

        final ObjectMapper objectMapper = new ObjectMapper();
        sce.getServletContext().setAttribute("objectMapper", objectMapper);

        ServletContextListener.super.contextInitialized(sce);
    }
}
