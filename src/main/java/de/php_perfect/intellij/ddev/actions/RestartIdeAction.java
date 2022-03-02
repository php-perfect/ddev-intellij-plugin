package de.php_perfect.intellij.ddev.actions;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.DumbAwareAction;
import de.php_perfect.intellij.ddev.DdevIntegrationBundle;
import org.jetbrains.annotations.NotNull;

public final class RestartIdeAction extends DumbAwareAction {
    public RestartIdeAction() {
        super(DdevIntegrationBundle.messagePointer("action.DdevIntegration.RestartIde.text"), DdevIntegrationBundle.messagePointer("action.DdevIntegration.RestartIde.description"), null);
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        ApplicationManager.getApplication().restart();
    }
}
