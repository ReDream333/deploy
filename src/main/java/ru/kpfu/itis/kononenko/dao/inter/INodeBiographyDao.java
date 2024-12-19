package ru.kpfu.itis.kononenko.dao.inter;

import ru.kpfu.itis.kononenko.entity.NodeBiography;

public interface INodeBiographyDao extends ICrud<NodeBiography>{
    String getByNodeId(Long nodeId);
    void deleteByNodeId(Long nodeId);
}
