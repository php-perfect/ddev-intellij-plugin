package de.php_perfect.intellij.ddev.actions;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import de.php_perfect.intellij.ddev.cmd.Description;
import de.php_perfect.intellij.ddev.state.DdevStateManager;
import de.php_perfect.intellij.ddev.state.State;
import de.php_perfect.intellij.ddev.terminal.DdevTerminalRunner;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.plugins.terminal.TerminalTabState;
import org.jetbrains.plugins.terminal.TerminalView;

public final class DdevPredefinedTerminalAction extends DdevAwareAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        Project project = e.getProject();

        if (project == null) {
            return;
        }

        DdevTerminalRunner runner = new DdevTerminalRunner(project);
        TerminalTabState tabState = new TerminalTabState();
        tabState.myTabName = "DDEV Web Container";

        TerminalView.getInstance(project).createNewSession(runner, tabState);
    }

    @Override
    protected boolean isActive(@NotNull Project project) {
        final State state = DdevStateManager.getInstance(project).getState();

        if (!state.isInstalled() || !state.isConfigured()) {
            return false;
        }

        Description description = state.getDescription();

        if (description == null) {
            return false;
        }

        return description.getStatus() == Description.Status.RUNNING;
    }
}
