package de.php_perfect.intellij.ddev.actions;

import com.intellij.icons.AllIcons;
import com.intellij.ide.BrowserUtil;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAwareAction;
import de.php_perfect.intellij.ddev.DdevIntegrationBundle;
import org.jetbrains.annotations.NotNull;

public final class InstallationInstructionsAction extends DumbAwareAction {
    public InstallationInstructionsAction() {
        super(DdevIntegrationBundle.messagePointer("action.DdevIntegration.InstallationInstructions.text"), DdevIntegrationBundle.messagePointer("action.DdevIntegration.InstallationInstructions.description"), AllIcons.Ide.External_link_arrow);
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        BrowserUtil.browse("https://ddev.readthedocs.io/en/stable/");
    }
}
