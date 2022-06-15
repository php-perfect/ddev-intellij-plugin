package de.php_perfect.intellij.ddev.actions;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.DumbAwareAction;
import com.intellij.openapi.project.Project;
import de.php_perfect.intellij.ddev.state.DdevStateManager;
import org.jetbrains.annotations.NotNull;

public final class SyncStateAction extends DumbAwareAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        Project project = e.getProject();

        if (project == null) {
            return;
        }

        ApplicationManager.getApplication().executeOnPooledThread(() -> DdevStateManager.getInstance(project).updateDescription());
    }
}
