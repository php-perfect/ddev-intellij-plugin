package de.php_perfect.intellij.ddev.activities;

import com.intellij.openapi.project.Project;
import de.php_perfect.intellij.ddev.DdevAwareActivity;
import de.php_perfect.intellij.ddev.settings.DdevSettingsState;
import de.php_perfect.intellij.ddev.state.DdevStateManager;
import de.php_perfect.intellij.ddev.state.State;
import de.php_perfect.intellij.ddev.version.VersionChecker;
import org.jetbrains.annotations.NotNull;

public final class CheckVersionActivity implements DdevAwareActivity {
    @Override
    public void runActivity(@NotNull Project project) {
        if (DdevSettingsState.getInstance(project).checkForUpdates) {
            VersionChecker.getInstance(project).checkDdevVersion();
        }

        State state = DdevStateManager.getInstance(project).getState();
        System.out.println(state.isInstalled());
        System.out.println(state.getVersions());
        System.out.println(state.isConfigured());
        System.out.println(state.getDescription());
    }
}
