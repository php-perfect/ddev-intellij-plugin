package de.php_perfect.intellij.ddev.cmd;

import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

public interface Ddev {

    @NotNull Versions version() throws CommandFailedException;

    @NotNull Description describe() throws CommandFailedException;

    static Ddev getInstance(@NotNull Project project) {
        return project.getService(Ddev.class);
    }
}
