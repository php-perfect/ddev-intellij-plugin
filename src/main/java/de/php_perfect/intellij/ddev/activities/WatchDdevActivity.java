package de.php_perfect.intellij.ddev.activities;

import com.intellij.openapi.project.Project;
import de.php_perfect.intellij.ddev.DdevAwareActivity;
import de.php_perfect.intellij.ddev.settings.DdevSettingsState;
import de.php_perfect.intellij.ddev.state.StateWatcher;
import org.jetbrains.annotations.NotNull;

public final class WatchDdevActivity implements DdevAwareActivity {
    @Override
    public void runActivity(@NotNull Project project) {
        if (!DdevSettingsState.getInstance(project).watchDdev) {
            return;
        }

        StateWatcher.getInstance(project).startWatching();
    }
}
