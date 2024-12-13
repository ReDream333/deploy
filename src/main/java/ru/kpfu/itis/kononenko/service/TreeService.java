package ru.kpfu.itis.kononenko.service;

import org.json.*;
import ru.kpfu.itis.kononenko.dao.NodeDao;
import ru.kpfu.itis.kononenko.dao.ParentChildRelationDao;
import ru.kpfu.itis.kononenko.dao.TreeDao;
import ru.kpfu.itis.kononenko.entity.Node;
import ru.kpfu.itis.kononenko.entity.ParentChildRelation;
import ru.kpfu.itis.kononenko.entity.Tree;
import ru.kpfu.itis.kononenko.util.Configuration;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class TreeService {
    private static final TreeDao treeDao = Configuration.getTreeDao();
    private static final NodeDao nodeDao = Configuration.getNodeDao();
    private static final ParentChildRelationDao relationDao = Configuration.getParentChildRelationDao();

    public long createTree(Long userId, String name, boolean isPrivate, Timestamp createdAt) {
        Tree tree = new Tree(
                null,
                userId,
                name,
                isPrivate,
                null,
                createdAt
        );
        return treeDao.save(tree);
    }

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
            jsonNode.put("biography", node.biography());
            jsonNode.put("photo", node.photo());
            jsonNodes.put(jsonNode);
        }

        return jsonNodes;
    }

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

    public boolean checkUserIdForThisTree(Long userId, Long treeId){
        //получили все деревья для юзера
        List<Tree> trees = treeDao.getAllForOneUser(userId);
        for(Tree tree: trees){
            if(Objects.equals(tree.id(), treeId)) return true;
        }
        return false;

    }

}
