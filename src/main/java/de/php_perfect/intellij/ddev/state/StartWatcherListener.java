package de.php_perfect.intellij.ddev.state;

import com.intellij.openapi.project.Project;
import de.php_perfect.intellij.ddev.StateInitializedListener;
import de.php_perfect.intellij.ddev.settings.DdevSettingsState;
import org.jetbrains.annotations.NotNull;

public final class StartWatcherListener implements StateInitializedListener {
    private final @NotNull Project project;

    public StartWatcherListener(@NotNull Project project) {
        this.project = project;
    }

    @Override
    public void onStateInitialized(@NotNull State state) {
        if (!DdevSettingsState.getInstance(this.project).watchDdev) {
            return;
        }

        StateWatcher.getInstance(this.project).startWatching();
    }
}
