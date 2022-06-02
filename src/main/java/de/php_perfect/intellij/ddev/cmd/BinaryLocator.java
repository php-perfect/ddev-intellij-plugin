package de.php_perfect.intellij.ddev.cmd;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface BinaryLocator {
    @Nullable String findInPath(@NotNull Project project);

    static BinaryLocator getInstance() {
        return ApplicationManager.getApplication().getService(BinaryLocator.class);
    }
}
