package de.php_perfect.intellij.ddev.actions;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import de.php_perfect.intellij.ddev.DdevIntegrationBundle;
import de.php_perfect.intellij.ddev.installer.DdevInstaller;
import org.jetbrains.annotations.NotNull;

// @todo: Remove?
public final class InstallDdevAction extends AnAction implements DumbAware {
    public InstallDdevAction() {
        super(DdevIntegrationBundle.messagePointer("actions.install"), AllIcons.Actions.Download);
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        final Project project = e.getProject();

        if (project == null) {
            return;
        }

        DdevInstaller.getInstance(project).install();
    }
}
