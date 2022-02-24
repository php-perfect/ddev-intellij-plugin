package de.php_perfect.intellij.ddev.database;

import com.intellij.database.dataSource.*;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import de.php_perfect.intellij.ddev.cmd.DatabaseInfo;
import de.php_perfect.intellij.ddev.cmd.Description;
import de.php_perfect.intellij.ddev.state.State;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DataSourceGeneratorImpl implements DataSourceGenerator {
    private final @NotNull Project project;

    public DataSourceGeneratorImpl(@NotNull Project project) {
        this.project = project;
    }

    public void generateDataSource(@NotNull State ddevState) {
        Description description = ddevState.getDescription();
        if (description == null) {
            return;
        }

        DatabaseInfo databaseInfo = description.getDatabaseInfo();
        if (databaseInfo == null) {
            return;
        }

        DatabaseInfo.Type databaseType = databaseInfo.getType();
        if (databaseType == null) {
            return;
        }

        DatabaseDriver driver = getDriverByDatabaseType(databaseType);
        if (driver == null) {
            return;
        }

        String connectionUrl = this.getDsnByDatabaseType(databaseInfo);
        LocalDataSource dataSource = buildDataSourceFromDriverAndUrl(driver, connectionUrl);

        registerDataSource(dataSource);
    }

    private void registerDataSource(LocalDataSource dataSource) {
        LocalDataSourceManager localDataSourceManager = LocalDataSourceManager.getInstance(this.project);

        ApplicationManager.getApplication().invokeLater(() -> {
            for (LocalDataSource currentLocalDataSource : localDataSourceManager.getDataSources()) {
                if (currentLocalDataSource.getName().equals(dataSource.getName())) {
                    localDataSourceManager.removeDataSource(currentLocalDataSource);
                }
            }

            localDataSourceManager.addDataSource(dataSource);
        });
    }

    private @NotNull LocalDataSource buildDataSourceFromDriverAndUrl(DatabaseDriver driver, String connectionUrl) {
        LocalDataSource dataSource = LocalDataSource.fromDriver(driver, connectionUrl, false);
        dataSource.setUsername("db");
        dataSource.setName("DDEV");
        dataSource.setComment("DDEV generated data source");
        dataSource.setConfiguredByUrl(true);
        dataSource.setSchemaControl(SchemaControl.AUTOMATIC);

        return dataSource;
    }

    private @Nullable DatabaseDriver getDriverByDatabaseType(@NotNull DatabaseInfo.Type databaseType) {
        DatabaseDriverManager instance = DatabaseDriverManagerImpl.getInstance();

        switch (databaseType) {
            case MYSQL:
                return instance.getDriver("mysql.8");
            case POSTGRES:
                return instance.getDriver("postgresql");
            default:
                return instance.getDriver("mariadb");
        }
    }

    private String getDsnByDatabaseType(@NotNull DatabaseInfo databaseInfo) {
        String dsnType = "mysql";

        if (databaseInfo.getType() == DatabaseInfo.Type.POSTGRES) {
            dsnType = "postgresql";
        }

        return String.format("jdbc:%s://localhost:%d/%s?user=%s&password=%s", dsnType, databaseInfo.getPublishedPort(), databaseInfo.getName(), databaseInfo.getUsername(), databaseInfo.getPassword());
    }
}
