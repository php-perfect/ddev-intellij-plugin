package de.php_perfect.intellij.ddev.actions;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.DumbAwareAction;
import com.intellij.openapi.project.Project;
import de.php_perfect.intellij.ddev.DdevIntegrationBundle;
import de.php_perfect.intellij.ddev.state.DdevStateManager;
import org.jetbrains.annotations.NotNull;

public final class ReloadPluginAction extends DumbAwareAction {
    public ReloadPluginAction() {
        super(DdevIntegrationBundle.messagePointer("action.DdevIntegration.ReloadPlugin.text"), DdevIntegrationBundle.messagePointer("action.DdevIntegration.ReloadPlugin.description"), AllIcons.Actions.Refresh);
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        Project project = e.getProject();

        if (project == null) {
            return;
        }

        ApplicationManager.getApplication().executeOnPooledThread(() -> DdevStateManager.getInstance(project).initialize());
    }
}
