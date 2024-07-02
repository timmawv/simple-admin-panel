package avlyakulov.timur.config.db;

import org.flywaydb.core.Flyway;

public class FlywayInitializer {

    private final Flyway flyway;

    public FlywayInitializer() {
        flyway = Flyway.configure()
                .dataSource("jdbc:postgresql://localhost:5432/users_db", "postgres", "postgres")
                .locations("classpath:db/migration")
                .load();
    }

    public void addMigrationsToDB() {
        flyway.migrate();
    }
}