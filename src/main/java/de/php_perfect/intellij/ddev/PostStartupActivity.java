package de.php_perfect.intellij.ddev;

import com.intellij.javaee.ExternalResourceManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.startup.StartupActivity;
import de.php_perfect.intellij.ddev.state.DdevStateManager;
import org.jetbrains.annotations.NotNull;

public class PostStartupActivity implements StartupActivity, StartupActivity.DumbAware {
    @Override
    public void runActivity(@NotNull Project project) {
        DdevStateManager.getInstance(project).initialize();
    }
}
