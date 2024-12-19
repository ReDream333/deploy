package ru.kpfu.itis.kononenko.dao.inter;

import ru.kpfu.itis.kononenko.entity.Node;

import java.util.List;

public interface INodeDao extends ICrud<Node>{
    List<Node> getNodesByTreeId(Long treeId);
    String getNameById(Long nodeId);
    void update(Node newNode);
}
