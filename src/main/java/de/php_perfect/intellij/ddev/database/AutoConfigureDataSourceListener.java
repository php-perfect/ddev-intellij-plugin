package de.php_perfect.intellij.ddev.database;

import com.intellij.database.dataSource.LocalDataSource;
import com.intellij.openapi.project.Project;
import de.php_perfect.intellij.ddev.DatabaseInfoChangedListener;
import de.php_perfect.intellij.ddev.cmd.DatabaseInfo;
import de.php_perfect.intellij.ddev.settings.DdevSettingsState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class AutoConfigureDataSourceListener implements DatabaseInfoChangedListener {
    private final @NotNull Project project;

    public AutoConfigureDataSourceListener(@NotNull Project project) {
        this.project = project;
    }

    @Override
    public void onDatabaseInfoChanged(@Nullable DatabaseInfo databaseInfo) {
        if (databaseInfo == null) {
            return;
        }

        if (!DdevSettingsState.getInstance(this.project).autoConfigureDataSource) {
            return;
        }

        LocalDataSource dataSource = DataSourceProvider.getInstance().buildDdevDataSource(databaseInfo);

        if (dataSource == null) {
            return;
        }

        DdevDataSourceManager.getInstance(this.project).updateDdevDataSource(dataSource);
    }
}
