package ru.kpfu.itis.kononenko.service;

import ru.kpfu.itis.kononenko.dao.NodeDao;
import ru.kpfu.itis.kononenko.dao.ParentChildRelationDao;
import ru.kpfu.itis.kononenko.entity.Node;
import ru.kpfu.itis.kononenko.entity.ParentChildRelation;
import ru.kpfu.itis.kononenko.service.inter.IFamilyService;
import ru.kpfu.itis.kononenko.util.Configuration;

public class FamilyService implements IFamilyService {
    private static final NodeDao nodeDao = Configuration.getNodeDao();
    private static final ParentChildRelationDao relationDao = Configuration.getParentChildRelationDao();

    @Override
    public void addInitialNode(Node selfNode) {
        nodeDao.save(selfNode);
    }

    @Override
    public Node addNewNode(Node newMember, Long childId) {
        Long newMemberId = nodeDao.save(newMember);
        relationDao.save(new ParentChildRelation(null, newMemberId, childId));
        return nodeDao.findById(newMemberId);

    }
}
