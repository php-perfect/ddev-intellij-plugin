package de.php_perfect.intellij.ddev;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.startup.StartupActivity;
import de.php_perfect.intellij.ddev.state.DdevStateManager;
import org.jetbrains.annotations.NotNull;

public final class InitPluginActivity implements StartupActivity, StartupActivity.DumbAware {
    @Override
    public void runActivity(@NotNull Project project) {
        ApplicationManager.getApplication().executeOnPooledThread(() -> DdevStateManager.getInstance(project).initialize());
    }
}
