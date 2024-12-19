package ru.kpfu.itis.kononenko.service;

import org.json.JSONArray;
import org.json.JSONObject;
import ru.kpfu.itis.kononenko.dao.NodePhotoDao;
import ru.kpfu.itis.kononenko.entity.NodePhoto;
import ru.kpfu.itis.kononenko.service.inter.INodePhotoService;
import ru.kpfu.itis.kononenko.util.Configuration;

import java.util.List;

public class NodePhotoService implements INodePhotoService {

    private static final NodePhotoDao photoDao= Configuration.getNodePhotoDao();

    @Override
    public NodePhoto addPhoto(Long nodeId, String photoUrl, String description){
        NodePhoto newPhoto = new NodePhoto(
                null,
                nodeId,
                photoUrl,
                description
        );
        Long id = photoDao.save(newPhoto);
        return photoDao.findById(id);
    }
    @Override
    public void deletePhoto(Long photoId){
        photoDao.deleteById(photoId);
    }

    @Override
    public NodePhoto updatePhotoDescription(Long id, String description) {
        photoDao.updateDescription(id, description);
        return photoDao.findById(id);
    }
    @Override
    public JSONArray convertPhotosToJson(Long nodeId){
        List<NodePhoto> photoArray = photoDao.getPhotosForOneNode(nodeId);
        JSONArray photoJsonArray = new JSONArray();

        for (NodePhoto photo : photoArray) {
            JSONObject jsonPhoto = new JSONObject();
            jsonPhoto.put("id", photo.id());
            jsonPhoto.put("photoUrl", photo.photoUrl());
            jsonPhoto.put("nodeId", photo.nodeId());
            jsonPhoto.put("description", photo.description());
            photoJsonArray.put(jsonPhoto);
        }
        return photoJsonArray;
    }
}
