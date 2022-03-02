package de.php_perfect.intellij.ddev.actions;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAwareAction;
import de.php_perfect.intellij.ddev.icons.DdevIntegrationIcons;
import de.php_perfect.intellij.ddev.terminal.DdevTerminalRunner;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.plugins.terminal.TerminalTabState;
import org.jetbrains.plugins.terminal.TerminalView;

public class DdevPredefinedTerminalAction extends DumbAwareAction {
    public DdevPredefinedTerminalAction() {
        super("DDEV Web Container", "Open DDEV web container terminal", DdevIntegrationIcons.DdevLogoColor);
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        DdevTerminalRunner runner = new DdevTerminalRunner(e.getProject());

        TerminalTabState tabState = new TerminalTabState();
        tabState.myTabName = "DDEV Web Container";

        TerminalView.getInstance(e.getProject()).createNewSession(runner, tabState);
    }
}
