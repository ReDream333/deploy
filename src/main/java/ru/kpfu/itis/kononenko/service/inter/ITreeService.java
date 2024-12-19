package ru.kpfu.itis.kononenko.service.inter;

import org.json.JSONArray;
import ru.kpfu.itis.kononenko.entity.Tree;

import java.sql.Timestamp;
import java.util.List;

public interface ITreeService {
    long createTree(Long userId, String name, boolean isPrivate, Timestamp createdAt);
    JSONArray convertNodesToJson(Long treeId);
    JSONArray convertRelationsToJson(Long treeId);
    boolean checkUserIdForThisTree(Long userId, Long treeId);
    void deleteAllTreeByUser(Long userId);
    List<Tree> getTreesByUserId(Long userId);
    List<Tree> getPublicTrees();
}
