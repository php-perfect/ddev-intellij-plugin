package de.php_perfect.intellij.ddev.actions;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.options.ShowSettingsUtil;
import com.intellij.openapi.project.DumbAwareAction;
import de.php_perfect.intellij.ddev.DdevIntegrationBundle;
import de.php_perfect.intellij.ddev.settings.DdevSettingsConfigurable;
import org.jetbrains.annotations.NotNull;

public final class ChangeSettingsAction extends DumbAwareAction {
    public ChangeSettingsAction() {
        super(DdevIntegrationBundle.messagePointer("action.DdevIntegration.ChangeSettings.text"), DdevIntegrationBundle.messagePointer("action.DdevIntegration.ChangeSettings.description"), null);
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        ShowSettingsUtil.getInstance().showSettingsDialog(e.getProject(), DdevSettingsConfigurable.getName());
    }
}
