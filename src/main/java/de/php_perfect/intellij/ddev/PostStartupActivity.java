package de.php_perfect.intellij.ddev;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.extensions.ExtensionPointName;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.startup.StartupActivity;
import com.intellij.testIntegration.TestFramework;
import de.php_perfect.intellij.ddev.state.DdevStateManager;
import org.jetbrains.annotations.NotNull;

public class PostStartupActivity implements StartupActivity, StartupActivity.DumbAware {
    private static final ExtensionPointName<DdevAwareActivity> EP_NAME = ExtensionPointName.create("de.php_perfect.intellij.ddev.ddevAwareActivity");

    @Override
    public void runActivity(@NotNull Project project) {
        DdevStateManager.getInstance(project).initialize(() -> {
            for (DdevAwareActivity extension : EP_NAME.getExtensionList()) {
                ApplicationManager.getApplication().executeOnPooledThread(() -> extension.runActivity(project));
            }
        });
    }
}
