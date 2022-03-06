package de.php_perfect.intellij.ddev.database;

import com.intellij.database.dataSource.LocalDataSource;
import com.intellij.database.dataSource.LocalDataSourceManager;
import com.intellij.database.util.DataSourceUtil;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

public final class DdevDataSourceManagerImpl implements DdevDataSourceManager {
    private final @NotNull Project project;

    public DdevDataSourceManagerImpl(@NotNull Project project) {
        this.project = project;
    }

    @Override
    public void updateDdevDataSource(@NotNull LocalDataSource dataSource) {
        LocalDataSourceManager localDataSourceManager = LocalDataSourceManager.getInstance(this.project);

        ApplicationManager.getApplication().invokeLater(() -> {
            for (LocalDataSource currentLocalDataSource : localDataSourceManager.getDataSources()) {
                if (currentLocalDataSource.getName().equals(dataSource.getName())) {
                    localDataSourceManager.removeDataSource(currentLocalDataSource);
                }
            }

            localDataSourceManager.addDataSource(dataSource);
            DataSourceUtil.performAutoSyncTask(this.project, dataSource);
        });
    }
}
