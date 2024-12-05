package ru.kpfu.itis.kononenko.util;

import ru.kpfu.itis.kononenko.dao.NodeDao;
import ru.kpfu.itis.kononenko.dao.ParentChildRelationDao;
import ru.kpfu.itis.kononenko.dao.TreeDao;
import ru.kpfu.itis.kononenko.dao.UserDao;
import ru.kpfu.itis.kononenko.exception.ConnectionException;
import ru.kpfu.itis.kononenko.mapper.NodeMapper;
import ru.kpfu.itis.kononenko.mapper.ParentChildRelationMapper;
import ru.kpfu.itis.kononenko.mapper.TreeMapper;
import ru.kpfu.itis.kononenko.mapper.UserMapper;


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

    public static ParentChildRelationDao getParentChildRelationDao(){
        return new ParentChildRelationDao(new ParentChildRelationMapper());
    }




    private static Connection connection;


    public static Connection getConnection() {
        Properties properties = new Properties();

        try {
            properties.load(new FileInputStream("E:\\IDE\\GTreeWithRes\\src\\main\\resources\\db.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (connection == null) {
            try {
                Class.forName("org.postgresql.Driver");
                connection = (DriverManager.getConnection(
                        properties.getProperty("db.url"),
                        properties.getProperty("db.username"),
                        properties.getProperty("db.password")
                ));
            } catch (SQLException e) {
                //прокидываем свою ошибку - в будущем надо еще и на сервере в качестве ответа перенаправить
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        return connection;
    }


}
