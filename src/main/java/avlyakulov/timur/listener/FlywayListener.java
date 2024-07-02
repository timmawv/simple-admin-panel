package avlyakulov.timur.listener;

import avlyakulov.timur.config.db.FlywayInitializer;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@WebListener
public class FlywayListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        FlywayInitializer flywayInitializer = new FlywayInitializer();
        flywayInitializer.addMigrationsToDB();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ServletContextListener.super.contextDestroyed(sce);
    }
}
