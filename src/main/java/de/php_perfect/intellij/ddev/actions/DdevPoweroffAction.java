package de.php_perfect.intellij.ddev.actions;

import com.intellij.openapi.project.Project;
import de.php_perfect.intellij.ddev.config.DdevConfigurationProvider;
import org.jetbrains.annotations.NotNull;

public class DdevPoweroffAction extends DdevRunAction {
    public DdevPoweroffAction() {
        super("poweroff");
    }

    protected boolean isActive(@NotNull Project project) {
        final DdevConfigurationProvider ddevConfigurationProvider = DdevConfigurationProvider.getInstance(project);

        return ddevConfigurationProvider.isInstalled() && ddevConfigurationProvider.isConfigured();
    }
}
