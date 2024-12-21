package ru.kpfu.itis.kononenko.util;

import ru.kpfu.itis.kononenko.dao.*;
import ru.kpfu.itis.kononenko.mapper.*;


import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class Configuration {

    public static UserDao getUserDao() {
        return new UserDao(new UserMapper());
    }

    public static TreeDao getTreeDao() {
        return new TreeDao(new TreeMapper());
    }

    public static NodeDao getNodeDao(){
        return new NodeDao(new NodeMapper());
    }

    public static NodePhotoDao getNodePhotoDao(){
        return new NodePhotoDao(new NodePhotoMapper());
    }

    public static ParentChildRelationDao getParentChildRelationDao(){
        return new ParentChildRelationDao(new ParentChildRelationMapper());
    }

    public static NodeBiographyDao getNodeBiographyDao(){
        return new NodeBiographyDao(new NodeBiographyMapper());
    }

    private static Connection connection;


    public static Connection getConnection() {
        String GTREE_DB_HOST = System.getenv("GTREE_DB_HOST");
        String GTREE_DB_NAME = System.getenv("GTREE_DB_NAME");
        String GTREE_DB_PASSWORD = System.getenv("GTREE_DB_PASSWORD");
        String GTREE_DB_PORT = System.getenv("GTREE_DB_PORT");
        String GTREE_DB_USERNAME = System.getenv("GTREE_DB_USERNAME");

        if (connection == null) {
            try {
                Class.forName("org.postgresql.Driver");
                connection = DriverManager.getConnection(
                        "jdbc:postgresql://%s:%s/%s"
                                .formatted(GTREE_DB_HOST, GTREE_DB_PORT, GTREE_DB_NAME),
                        GTREE_DB_USERNAME,
                        GTREE_DB_PASSWORD
                );
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        return connection;
    }


}
