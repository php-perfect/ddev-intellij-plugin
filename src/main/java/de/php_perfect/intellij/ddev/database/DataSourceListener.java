package de.php_perfect.intellij.ddev.database;

import com.intellij.openapi.project.Project;
import de.php_perfect.intellij.ddev.DescriptionChangedListener;
import de.php_perfect.intellij.ddev.cmd.Description;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DataSourceListener implements DescriptionChangedListener {
    private final @NotNull Project project;

    public DataSourceListener(@NotNull Project project) {
        this.project = project;
    }

    @Override
    public void onDescriptionChanged(@Nullable Description description) {
        DataSourceGenerator.getInstance(project).generateDataSource(description);
    }
}
