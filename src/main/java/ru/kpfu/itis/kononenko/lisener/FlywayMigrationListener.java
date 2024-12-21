package ru.kpfu.itis.kononenko.lisener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;


@Slf4j
@WebListener
public class FlywayMigrationListener implements ServletContextListener {

    String PGHOST = System.getenv("PGHOST");
    String POSTGRES_DB = System.getenv("POSTGRES_DB");
    String PGPASSWORD = System.getenv("PGPASSWORD");
    String PGPORT = System.getenv("PGPORT");
    String PGUSER = System.getenv("PGUSER");

    public void contextInitialized(ServletContextEvent sce) {
        try{

            String url = "jdbc:postgresql://%s:%s/%s"
                    .formatted(PGHOST, PGPORT, POSTGRES_DB);

            Flyway flyWay = Flyway
                    .configure()
                    .dataSource(url, PGUSER, PGPASSWORD)
                    .load();
            flyWay.migrate();
            log.info("YES MIGRATION");
        } catch (Exception e){
            log.error(e.getMessage());
            log.error("WHAT NOOOOOOOOOO");
        }
    }


}
