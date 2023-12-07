package de.php_perfect.intellij.ddev.actions;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.options.ShowSettingsUtil;
import com.intellij.openapi.project.DumbAwareAction;
import de.php_perfect.intellij.ddev.DdevIntegrationBundle;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public final class ManagePluginsAction extends DumbAwareAction {
    public ManagePluginsAction() {
        super(DdevIntegrationBundle.messagePointer("action.DdevIntegration.ManagePlugins.text"), DdevIntegrationBundle.messagePointer("action.DdevIntegration.ManagePlugins.description"), (Icon) null);
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        var project = e.getProject();

        if (project == null) {
            return;
        }

        ShowSettingsUtil.getInstance().showSettingsDialog(project, "Plugins");
    }
}
