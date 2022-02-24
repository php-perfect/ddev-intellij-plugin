package de.php_perfect.intellij.ddev.database;

import com.intellij.openapi.project.Project;
import de.php_perfect.intellij.ddev.state.State;
import org.jetbrains.annotations.NotNull;

public interface DataSourceGenerator {
    void generateDataSource(@NotNull State ddevState);

    static DataSourceGenerator getInstance(@NotNull Project project) {
        return project.getService(DataSourceGenerator.class);
    }
}
