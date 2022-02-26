package de.php_perfect.intellij.ddev.database;

import com.intellij.openapi.project.Project;
import de.php_perfect.intellij.ddev.cmd.Description;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface DataSourceGenerator {
    void generateDataSource(@Nullable Description description);

    static DataSourceGenerator getInstance(@NotNull Project project) {
        return project.getService(DataSourceGenerator.class);
    }
}
