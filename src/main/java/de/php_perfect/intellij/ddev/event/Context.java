package de.php_perfect.intellij.ddev.event;

import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

public class Context {
    private final @NotNull Project project;

    public Context(@NotNull Project project) {
        this.project = project;
    }

    public @NotNull Project getProject() {
        return project;
    }
}
