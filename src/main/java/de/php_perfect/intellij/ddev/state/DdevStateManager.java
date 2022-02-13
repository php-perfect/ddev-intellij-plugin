package de.php_perfect.intellij.ddev.state;

import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

public interface DdevStateManager {

    @NotNull State getState();

    void initialize();

    void updateState();

    void startWatching();

    void stopWatching();

    static DdevStateManager getInstance(@NotNull Project project) {
        return project.getService(DdevStateManager.class);
    }
}
