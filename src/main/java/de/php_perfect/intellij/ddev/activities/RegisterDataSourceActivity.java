package de.php_perfect.intellij.ddev.activities;

import com.intellij.database.dataSource.*;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import de.php_perfect.intellij.ddev.DdevAwareActivity;
import org.jetbrains.annotations.NotNull;

public class RegisterDataSourceActivity implements DdevAwareActivity {
    @Override
    public void runActivity(@NotNull Project project) {
        DatabaseDriverManager instance = DatabaseDriverManagerImpl.getInstance();
        DatabaseDriver driver = instance.getDriver("mysql.8");

        String connectionUrl = "jdbc:mysql://db:db@localhost:56348/db";
        LocalDataSource dataSource = LocalDataSource.fromDriver(driver, connectionUrl, false);
        dataSource.setUsername("db");
        dataSource.setName("DDEV Test");
        dataSource.setComment("DDEV generated data source");
        dataSource.setConfiguredByUrl(true);
        dataSource.setSchemaControl(SchemaControl.AUTOMATIC);

        LocalDataSourceManager localDataSourceManager = LocalDataSourceManager.getInstance(project);

        ApplicationManager.getApplication().invokeLater(() -> {
            for (LocalDataSource currentLocalDataSource : localDataSourceManager.getDataSources()) {
                if (currentLocalDataSource.getName().equals(dataSource.getName())) {
                    localDataSourceManager.removeDataSource(currentLocalDataSource);

                }
            }

            localDataSourceManager.addDataSource(dataSource);
        });
    }
}
