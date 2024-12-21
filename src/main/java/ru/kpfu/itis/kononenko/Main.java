package ru.kpfu.itis.kononenko;

import ru.kpfu.itis.kononenko.dao.NodeDao;
import ru.kpfu.itis.kononenko.dao.NodePhotoDao;
import ru.kpfu.itis.kononenko.dao.UserDao;
import ru.kpfu.itis.kononenko.entity.NodePhoto;
import ru.kpfu.itis.kononenko.service.NodePhotoService;
import ru.kpfu.itis.kononenko.service.NodeService;
import ru.kpfu.itis.kononenko.service.TreeService;
import ru.kpfu.itis.kononenko.service.UserService;
import ru.kpfu.itis.kononenko.util.Configuration;

public class Main {
    public static void main(String[] args) {
//        UserDao u = Configuration.getUserDao();
//        UserService userService  = new UserService();
//
//        TreeService t = new TreeService();
//        System.out.println(t.convertRelationsToJson(1L).toString(1));
//        System.out.println(t.convertNodesToJson(3L).toString(1));

//        NodePhotoDao p = Configuration.getNodePhotoDao();
//        System.out.println(p.getAllForOneNode(1L));
//        NodeDao n = Configuration.getNodeDao();
//        System.out.println(n.getNameById(1L));
//        NodePhotoDao np = Configuration.getNodePhotoDao();
//        Long i = np.save(new NodePhoto(null, 1L, "типо путь", "новое фото"));
//        System.out.println(np.findById(i));
//        System.out.println(np.getPhotosForOneNode(1L));
//
//        NodeService nodeService = new NodeService();
//        NodePhotoService nodePhotoService = new NodePhotoService();
//        System.out.println(nodePhotoService.convertPhotosToJson(1L));

        String db = "db.password";
        System.out.println(db);

    }
}
