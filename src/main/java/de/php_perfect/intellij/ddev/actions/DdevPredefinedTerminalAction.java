package de.php_perfect.intellij.ddev.actions;

import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAwareAction;
import de.php_perfect.intellij.ddev.cmd.wsl.WslAware;
import de.php_perfect.intellij.ddev.icons.DdevIntegrationIcons;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.plugins.terminal.LocalTerminalDirectRunner;
import org.jetbrains.plugins.terminal.TerminalTabState;
import org.jetbrains.plugins.terminal.TerminalView;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class DdevPredefinedTerminalAction extends DumbAwareAction {
    public DdevPredefinedTerminalAction() {
        super("DDEV Web Container", "Open DDEV web container terminal", DdevIntegrationIcons.DdevLogoColor);
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        GeneralCommandLine command = new GeneralCommandLine("ddev", "ssh");
        command.setWorkDirectory(e.getProject().getBasePath());
        command = WslAware.patchCommandLine(command);

        final String finalCommand = command.getCommandLineString();

        LocalTerminalDirectRunner runner = new LocalTerminalDirectRunner(e.getProject()) {
            @Override
            public @NotNull List<String> getInitialCommand(@NotNull Map<String, String> envs) {
                return Arrays.asList(finalCommand.split(" "));
            }
        };

        TerminalTabState tabState = new TerminalTabState();
        tabState.myTabName = "DDEV Web Container";
        TerminalView.getInstance(e.getProject()).createNewSession(runner, tabState);
    }
}
