package ru.kpfu.itis.kononenko.service.inter;

import org.json.JSONArray;
import ru.kpfu.itis.kononenko.entity.NodePhoto;

public interface INodePhotoService {
    NodePhoto addPhoto(Long nodeId, String photoUrl, String description);
    void deletePhoto(Long photoId);
    NodePhoto updatePhotoDescription(Long id, String description);
    JSONArray convertPhotosToJson(Long nodeId);

}
