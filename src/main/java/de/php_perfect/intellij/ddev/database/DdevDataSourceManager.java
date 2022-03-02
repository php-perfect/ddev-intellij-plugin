package de.php_perfect.intellij.ddev.database;

import com.intellij.database.dataSource.LocalDataSource;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

public interface DdevDataSourceManager {
    void updateDdevDataSource(@NotNull LocalDataSource dataSource);

    static DdevDataSourceManager getInstance(@NotNull Project project) {
        return project.getService(DdevDataSourceManager.class);
    }
}
