package de.php_perfect.intellij.ddev.database;

import com.intellij.database.dataSource.LocalDataSource;
import com.intellij.openapi.application.ApplicationManager;
import de.php_perfect.intellij.ddev.cmd.DatabaseInfo;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface DataSourceProvider {
    @Nullable LocalDataSource buildDdevDataSource(@NotNull DatabaseInfo databaseInfo);

    static DataSourceProvider getInstance() {
        return ApplicationManager.getApplication().getService(DataSourceProvider.class);
    }
}
