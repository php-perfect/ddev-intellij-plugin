package de.php_perfect.intellij.ddev.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import de.php_perfect.intellij.ddev.DdevIntegrationBundle;
import de.php_perfect.intellij.ddev.settings.DdevSettingsState;
import org.jetbrains.annotations.NotNull;

public final class DisableCheckForUpdatesAction extends AnAction implements DumbAware {
    public DisableCheckForUpdatesAction() {
        super(DdevIntegrationBundle.messagePointer("actions.disable_check_for_updates"));
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        final Project project = e.getProject();

        if (project == null) {
            return;
        }

        DdevSettingsState.getInstance(project).checkForUpdates = false;
    }
}
