package ru.kpfu.itis.kononenko.service.inter;

import ru.kpfu.itis.kononenko.entity.Node;

import java.sql.Date;

public interface INodeService {
    String getNodeName(Long nodeId);
    Node updateNode(Long id, String firstName, String lastName, Date birthDate, Date deathDate, String photo);
    void deleteNode(Long nodeId);
}
