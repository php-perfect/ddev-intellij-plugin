package de.php_perfect.intellij.ddev.activities;

import com.intellij.openapi.project.Project;
import de.php_perfect.intellij.ddev.DdevAwareActivity;
import de.php_perfect.intellij.ddev.state.DdevStateManager;
import de.php_perfect.intellij.ddev.settings.DdevSettingsState;
import org.jetbrains.annotations.NotNull;

public class WatchDdevActivity implements DdevAwareActivity {
    @Override
    public void runActivity(@NotNull Project project) {
        DdevStateManager ddevConfigurationProvider = DdevStateManager.getInstance(project);

        if (DdevSettingsState.getInstance(project).watchDdev) {
            ddevConfigurationProvider.startWatching();
        }
    }
}
