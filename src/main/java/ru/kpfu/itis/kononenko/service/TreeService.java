package ru.kpfu.itis.kononenko.service;

import org.json.*;
import ru.kpfu.itis.kononenko.dao.NodeDao;
import ru.kpfu.itis.kononenko.dao.ParentChildRelationDao;
import ru.kpfu.itis.kononenko.dao.TreeDao;
import ru.kpfu.itis.kononenko.entity.Node;
import ru.kpfu.itis.kononenko.entity.ParentChildRelation;
import ru.kpfu.itis.kononenko.entity.Tree;
import ru.kpfu.itis.kononenko.service.inter.ITreeService;
import ru.kpfu.itis.kononenko.util.Configuration;

import java.sql.Timestamp;
import java.util.List;

public class TreeService implements ITreeService {
    private static final TreeDao treeDao = Configuration.getTreeDao();
    private static final NodeDao nodeDao = Configuration.getNodeDao();
    private static final ParentChildRelationDao relationDao = Configuration.getParentChildRelationDao();

    @Override
    public long createTree(Long userId, String name, boolean isPrivate, Timestamp createdAt) {
        Tree tree = new Tree(
                null,
                userId,
                name,
                isPrivate,
                createdAt
        );
        return treeDao.save(tree);
    }

    @Override
    public JSONArray convertNodesToJson(Long treeId) {
        List<Node> nodes = nodeDao.getNodesByTreeId(treeId);
        JSONArray jsonNodes = new JSONArray();

        // Формируем JSON для узлов
        for (Node node : nodes) {
            JSONObject jsonNode = new JSONObject();
            jsonNode.put("key", node.id());
            jsonNode.put("fullName", node.firstName() + " " + node.lastName());
            jsonNode.put("gender", String.valueOf(node.gender()));
            jsonNode.put("birthday", String.valueOf(node.birthDate()));
            jsonNode.put("death", String.valueOf(node.deathDate()));
            jsonNode.put("comment", node.comment());
            jsonNode.put("photo", node.photo());
            jsonNodes.put(jsonNode);
        }

        return jsonNodes;
    }

    @Override
    public JSONArray convertRelationsToJson(Long treeId) {
        List<ParentChildRelation> relations = relationDao.getRelationsByTreeId(treeId);
        JSONArray jsonLinks = new JSONArray();

        // Формируем JSON для связей
        for (ParentChildRelation relation : relations) {
            JSONObject jsonLink = new JSONObject();
            jsonLink.put("from", relation.childId());
            jsonLink.put("to", relation.parentId());
            jsonLinks.put(jsonLink);
        }

        return jsonLinks;
    }

    @Override
    public boolean checkUserIdForThisTree(Long userId, Long treeId){
        Tree tree = treeDao.findById(treeId);
        return tree.userId().equals(userId);
    }

    @Override
    public void deleteAllTreeByUser(Long userId) {
        treeDao.deleteByUserId(userId);
    }

    @Override
    public List<Tree> getTreesByUserId(Long userId) {
        return treeDao.getAllForOneUser(userId);
    }

    @Override
    public List<Tree> getPublicTrees() {
        return treeDao.getPublic();
    }

    public Tree findById(Long treeId) {
        return treeDao.findById(treeId);
    }
}
