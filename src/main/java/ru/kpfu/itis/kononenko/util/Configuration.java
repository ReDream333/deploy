package ru.kpfu.itis.kononenko.util;

import lombok.extern.slf4j.Slf4j;
import ru.kpfu.itis.kononenko.dao.*;
import ru.kpfu.itis.kononenko.mapper.*;


import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


@Slf4j
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
        String PGHOST = System.getenv("PGHOST");
        String POSTGRES_DB = System.getenv("POSTGRES_DB");
        String PGPASSWORD = System.getenv("PGPASSWORD");
        String PGPORT = System.getenv("PGPORT");
        String PGUSER = System.getenv("PGUSER");

        if (connection == null) {
            try {
                Class.forName("org.postgresql.Driver");
                connection = DriverManager.getConnection(
                        "jdbc:postgresql://%s:%s/%s"
                                .formatted(PGHOST, PGPORT, POSTGRES_DB),
                        PGUSER,
                        PGPASSWORD
                );
                log.info("yeeeeees");
            } catch (SQLException | ClassNotFoundException e) {
                log.info("nooooooooooooooo(");
                log.error(e.getMessage());
                log.error(System.getenv("PGHOST"));
                log.error(System.getenv("POSTGRES_DB"));
                log.error(System.getenv("PGPASSWORD"));
                log.error(System.getenv("PGPORT"));
                log.error(System.getenv("PGUSER"));
                e.printStackTrace();
            }
        }

        return connection;
    }


}
