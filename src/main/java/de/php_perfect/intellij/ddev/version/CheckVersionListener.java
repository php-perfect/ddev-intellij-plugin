package de.php_perfect.intellij.ddev.version;

import com.intellij.openapi.project.Project;
import de.php_perfect.intellij.ddev.StateInitializedListener;
import de.php_perfect.intellij.ddev.settings.DdevSettingsState;
import de.php_perfect.intellij.ddev.state.State;
import org.jetbrains.annotations.NotNull;

public final class CheckVersionListener implements StateInitializedListener {
    private final @NotNull Project project;

    public CheckVersionListener(@NotNull Project project) {
        this.project = project;
    }

    @Override
    public void onStateInitialized(@NotNull State state) {
        if (DdevSettingsState.getInstance(this.project).checkForUpdates) {
            VersionChecker.getInstance(this.project).checkDdevVersion();
        }
    }
}
