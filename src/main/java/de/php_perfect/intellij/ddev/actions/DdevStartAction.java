package de.php_perfect.intellij.ddev.actions;

import com.intellij.openapi.project.Project;
import de.php_perfect.intellij.ddev.cmd.Description;
import de.php_perfect.intellij.ddev.config.DdevStateManager;
import org.jetbrains.annotations.NotNull;

public class DdevStartAction extends DdevRunAction {
    public DdevStartAction() {
        super("start");
    }

    protected boolean isActive(@NotNull Project project) {
        final DdevStateManager ddevConfigurationProvider = DdevStateManager.getInstance(project);

        if (!ddevConfigurationProvider.isInstalled()) {
            return false;
        }

        Description status = ddevConfigurationProvider.getStatus();

        if (status == null) {
            return false;
        }

        return status.getStatus() != Description.Status.RUNNING && status.getStatus() != Description.Status.STARTING && status.getStatus() != Description.Status.UNDEFINED;
    }
}
