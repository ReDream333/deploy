package ru.kpfu.itis.kononenko.dao.inter;

import ru.kpfu.itis.kononenko.entity.NodePhoto;

import java.util.List;

public interface INodePhotoDao extends ICrud<NodePhoto> {
    List<NodePhoto> getPhotosForOneNode(Long nodeId);
    void updateDescription(Long id, String description);
}
