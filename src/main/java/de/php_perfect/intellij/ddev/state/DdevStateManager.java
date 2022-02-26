package de.php_perfect.intellij.ddev.state;

import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface DdevStateManager {
    @NotNull State getState();

    void initialize(@Nullable Runnable afterInit);

    void updateDescription();

    static DdevStateManager getInstance(@NotNull Project project) {
        return project.getService(DdevStateManager.class);
    }
}
