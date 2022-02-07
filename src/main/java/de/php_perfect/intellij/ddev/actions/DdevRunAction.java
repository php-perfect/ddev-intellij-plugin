package de.php_perfect.intellij.ddev.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import de.php_perfect.intellij.ddev.cmd.DdevRunner;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Logger;

abstract class DdevRunAction extends AnAction {
    private final String ddevAction;

    public DdevRunAction(@NotNull String ddevAction) {
        this.ddevAction = ddevAction;
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        if (e.getProject() == null) {
            Logger.getGlobal().warning("No active project found");
            return;
        }

        DdevRunner.getInstance(e.getProject()).runDdev(e.getPresentation().getText(), ddevAction);
    }

    abstract protected boolean isActive(@NotNull Project project);

    @Override
    public void update(@NotNull AnActionEvent e) {
        final Project project = e.getProject();

        if (project == null) {
            e.getPresentation().setEnabled(false);
            return;
        }

        e.getPresentation().setEnabled(this.isActive(project));
    }
}
