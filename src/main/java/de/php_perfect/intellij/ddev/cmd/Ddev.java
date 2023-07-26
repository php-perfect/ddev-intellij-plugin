package de.php_perfect.intellij.ddev.cmd;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import de.php_perfect.intellij.ddev.version.Version;
import org.jetbrains.annotations.NotNull;

public interface Ddev {
    @NotNull Version version(@NotNull String binary, @NotNull Project project) throws CommandFailedException;

    @NotNull Versions detailedVersions(@NotNull String binary, @NotNull Project project) throws CommandFailedException;

    @NotNull Description describe(@NotNull String binary, @NotNull Project project) throws CommandFailedException;

    static Ddev getInstance() {
        return ApplicationManager.getApplication().getService(Ddev.class);
    }
}
