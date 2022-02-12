package de.php_perfect.intellij.ddev.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import de.php_perfect.intellij.ddev.settings.DdevSettingsState;
import org.jetbrains.annotations.NotNull;

public class DoNotCheckForUpdatesAction extends AnAction {
    public DoNotCheckForUpdatesAction() {
        super("Do not check for updates");
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
