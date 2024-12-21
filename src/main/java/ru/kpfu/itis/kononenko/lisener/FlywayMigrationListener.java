package ru.kpfu.itis.kononenko.lisener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;


@Slf4j
@WebListener
public class FlywayMigrationListener implements ServletContextListener {

    private static final String URL_KEY = "postgresql://postgres:bmVnPtEmWyUFbAVDZXIPagHcCxYjqjJb@autorack.proxy.rlwy.net:24011/railway";
    private static final String USERNAME_KEY = "postgres";
    private static final String PASSWORD_KEY = "zmlOdCfhHMUeRkDzzmeOEQNCjRDjMxxC";

    public void contextInitialized(ServletContextEvent sce) {
        try{
            Flyway flyWay = Flyway
                    .configure()
                    .dataSource(URL_KEY, USERNAME_KEY, PASSWORD_KEY)
                    .load();
            flyWay.migrate();
            log.info("YES MIGRATION");
        } catch (Exception e){
            log.error(e.getMessage());
            log.error("WHAT NOOOOOOOOOO");
        }
    }


}
