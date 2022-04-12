package de.php_perfect.intellij.ddev.state;

import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.TestOnly;

public interface DdevStateManager {
    @NotNull State getState();

    void initialize(@Nullable Runnable afterInit);

    void updateVersion();

    void updateConfiguration();

    void updateDescription();

    @TestOnly
    void resetState();

    static DdevStateManager getInstance(@NotNull Project project) {
        return project.getService(DdevStateManager.class);
    }
}
