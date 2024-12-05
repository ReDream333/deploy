package ru.kpfu.itis.kononenko.service;

import ru.kpfu.itis.kononenko.dao.NodeDao;
import ru.kpfu.itis.kononenko.dao.ParentChildRelationDao;
import ru.kpfu.itis.kononenko.entity.Node;
import ru.kpfu.itis.kononenko.entity.ParentChildRelation;
import ru.kpfu.itis.kononenko.util.Configuration;

public class FamilyService {
    private static final NodeDao nodeDao = Configuration.getNodeDao();
    private static final ParentChildRelationDao relationDao = Configuration.getParentChildRelationDao();

    public void addInitialNodes(Long treeId, Node selfNode, Node motherNode, Node fatherNode) {
        Long selfId = nodeDao.save(selfNode);

        if (motherNode != null) {
            Long motherNodeId = nodeDao.save(motherNode);
            relationDao.save(new ParentChildRelation(null, motherNodeId, selfId));
        }

        if (fatherNode != null) {
            Long fatherNodeId = nodeDao.save(fatherNode);
            relationDao.save(new ParentChildRelation(null, fatherNodeId, selfId));
        }
    }
}
