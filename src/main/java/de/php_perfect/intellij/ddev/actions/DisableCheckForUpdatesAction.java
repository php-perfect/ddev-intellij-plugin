package de.php_perfect.intellij.ddev.actions;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAwareAction;
import com.intellij.openapi.project.Project;
import de.php_perfect.intellij.ddev.DdevIntegrationBundle;
import de.php_perfect.intellij.ddev.settings.DdevSettingsState;
import org.jetbrains.annotations.NotNull;

public final class DisableCheckForUpdatesAction extends DumbAwareAction {
    public DisableCheckForUpdatesAction() {
        super(DdevIntegrationBundle.messagePointer("actions.disableCheckForUpdates"));
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
