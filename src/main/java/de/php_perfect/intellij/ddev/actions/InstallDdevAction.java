package de.php_perfect.intellij.ddev.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import de.php_perfect.intellij.ddev.installer.DdevInstaller;
import org.jetbrains.annotations.NotNull;

public final class InstallDdevAction extends AnAction {
    public InstallDdevAction(String title) {
        super(title);
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
