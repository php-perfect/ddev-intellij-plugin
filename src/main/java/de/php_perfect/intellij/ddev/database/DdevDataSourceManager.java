package de.php_perfect.intellij.ddev.database;

import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

public interface DdevDataSourceManager {
    void updateDdevDataSource(final @NotNull DataSourceConfig dataSourceConfig);

    static DdevDataSourceManager getInstance(final @NotNull Project project) {
        return project.getService(DdevDataSourceManager.class);
    }
}
