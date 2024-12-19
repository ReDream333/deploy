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
        Properties properties = new Properties();

        try {
            properties.load(new FileInputStream("E:\\IDE\\GTreeWithRes\\src\\main\\resources\\db.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (connection == null) {
            try {
                Class.forName("org.postgresql.Driver");
                connection = (DriverManager.getConnection(
                        properties.getProperty("db.url"),
                        properties.getProperty("db.username"),
                        properties.getProperty("db.password")
                ));
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        return connection;
    }


}
