package ru.kpfu.itis.kononenko.service;

import ru.kpfu.itis.kononenko.dao.NodeBiographyDao;
import ru.kpfu.itis.kononenko.entity.NodeBiography;
import ru.kpfu.itis.kononenko.service.inter.INodeBiographyService;
import ru.kpfu.itis.kononenko.util.Configuration;

public class NodeBiographyService implements INodeBiographyService {
    private static final NodeBiographyDao biographyDao= Configuration.getNodeBiographyDao();


    @Override
    public void saveBiography(Long nodeId, String biography) {
        NodeBiography newBio = new NodeBiography(
                null,
                nodeId,
                biography
        );
        biographyDao.save(newBio);
    }

    @Override
    public void deleteBiography(Long nodeId) {
        biographyDao.deleteByNodeId(nodeId);
    }

    @Override
    public String getBiography(Long nodeId) {
        return biographyDao.getByNodeId(nodeId);
    }
}
