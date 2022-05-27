package de.php_perfect.intellij.ddev;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.extensions.ExtensionPointName;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.startup.StartupActivity;
import de.php_perfect.intellij.ddev.error_reporting.SentrySdkInitializer;
import de.php_perfect.intellij.ddev.state.DdevStateManager;
import org.jetbrains.annotations.NotNull;

public final class InitPluginActivity implements StartupActivity, StartupActivity.DumbAware {
    private static final ExtensionPointName<DdevAwareActivity> EP_NAME = ExtensionPointName.create("de.php_perfect.intellij.ddev.ddevAwareActivity");

    @Override
    public void runActivity(@NotNull Project project) {
        DdevStateManager.getInstance(project).initialize(() -> {
            for (DdevAwareActivity extension : EP_NAME.getExtensionList()) {
                ApplicationManager.getApplication().executeOnPooledThread(() -> extension.runActivity(project));
            }
        });

        SentrySdkInitializer.init();
    }
}
