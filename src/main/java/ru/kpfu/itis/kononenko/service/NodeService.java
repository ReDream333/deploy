package ru.kpfu.itis.kononenko.service;

import ru.kpfu.itis.kononenko.dao.NodeDao;
import ru.kpfu.itis.kononenko.entity.Node;
import ru.kpfu.itis.kononenko.service.inter.INodeService;
import ru.kpfu.itis.kononenko.util.Configuration;

import java.sql.Date;

public class NodeService implements INodeService {

    private static final NodeDao nodeDao= Configuration.getNodeDao();

    @Override
    public String getNodeName(Long nodeId){
        return nodeDao.getNameById(nodeId);
    }

    @Override
    public Node updateNode(Long id, String firstName, String lastName, Date birthDate, Date deathDate, String photo){
        Node newNode = new Node(
                id,
                null,
                firstName,
                lastName,
                'M',
                birthDate,
                deathDate,
                null,
                photo
        );
        nodeDao.update(newNode);
        return nodeDao.findById(id);
    }

    @Override
    public void deleteNode(Long nodeId) {
        nodeDao.deleteById(nodeId);
    }

}
