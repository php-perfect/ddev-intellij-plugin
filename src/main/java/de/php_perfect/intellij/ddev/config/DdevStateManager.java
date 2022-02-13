package de.php_perfect.intellij.ddev.config;

import com.intellij.openapi.project.Project;
import de.php_perfect.intellij.ddev.cmd.Description;
import de.php_perfect.intellij.ddev.cmd.Versions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

// @Todo Reduce to getState() and update methods
public interface DdevStateManager {

    @Nullable Versions getVersions();

    boolean isInstalled();

    @Nullable Description getStatus();

    boolean isConfigured();

    void updateStatus();

    void initialize();

    void startWatcher();

    void stopWatcher();

    static DdevStateManager getInstance(@NotNull Project project) {
        return project.getService(DdevStateManager.class);
    }
}
