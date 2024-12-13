package ru.kpfu.itis.kononenko.service;

import org.json.JSONArray;
import org.json.JSONObject;
import ru.kpfu.itis.kononenko.dao.NodeDao;
import ru.kpfu.itis.kononenko.dao.NodePhotoDao;
import ru.kpfu.itis.kononenko.entity.Node;
import ru.kpfu.itis.kononenko.entity.NodePhoto;
import ru.kpfu.itis.kononenko.util.Configuration;

import java.sql.Date;
import java.util.List;

public class NodeService {
    private static final NodePhotoDao photoDao= Configuration.getNodePhotoDao();
    private static final NodeDao nodeDao= Configuration.getNodeDao();

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

    public String getNodeName(Long nodeId){
        return nodeDao.getNameById(nodeId);
    }

    public Node updateNode(Long id, String firstName, String lastName, Date birthDate, Date deathDate, String photo){
        Node newNode = new Node(
                id,
                null,
                firstName,
                lastName,
                null,
                'M',
                birthDate,
                deathDate,
                null,
                photo
        );
        nodeDao.update(newNode);
        return nodeDao.findById(id);
    }

    public void deleteNode(Long nodeId) {
        nodeDao.deleteById(nodeId);
    }

    public void deletePhoto(Long photoId){
        photoDao.deleteById(photoId);
    }

    public NodePhoto updatePhotoDescription(Long id, String description) {
        photoDao.updateDescription(id, description);
        return photoDao.findById(id);
    }
}
