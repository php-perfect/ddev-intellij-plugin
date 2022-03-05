package de.php_perfect.intellij.ddev.terminal;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.project.Project;
import de.php_perfect.intellij.ddev.actions.DdevPredefinedTerminalAction;
import de.php_perfect.intellij.ddev.cmd.Description;
import de.php_perfect.intellij.ddev.state.DdevStateManager;
import de.php_perfect.intellij.ddev.state.State;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.plugins.terminal.ui.OpenPredefinedTerminalActionProvider;

import java.util.ArrayList;
import java.util.List;

public class DdevPredefinedTerminalActionProvider implements OpenPredefinedTerminalActionProvider {
    @NotNull
    @Override
    public List<AnAction> listOpenPredefinedTerminalActions(@NotNull Project project) {
        List<AnAction> actions = new ArrayList<>();

        State state = DdevStateManager.getInstance(project).getState();
        Description description = state.getDescription();

        if (description == null) {
            return actions;
        }

        Description.Status status = description.getStatus();

        if (status == null) {
            return actions;
        }

        if (description.getStatus() == Description.Status.RUNNING) {
            actions.add(new DdevPredefinedTerminalAction());
        }

        return actions;
    }
}
