package de.php_perfect.intellij.ddev.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import de.php_perfect.intellij.ddev.cmd.DdevRunner;
import org.jetbrains.annotations.NotNull;

abstract class DdevRunAction extends AnAction implements DumbAware {
    private final String ddevAction;

    public DdevRunAction(@NotNull String ddevAction) {
        this.ddevAction = ddevAction;
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        final Project project = e.getProject();

        if (project == null) {
            return;
        }

        DdevRunner.getInstance(project).runDdev(this.ddevAction);
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
