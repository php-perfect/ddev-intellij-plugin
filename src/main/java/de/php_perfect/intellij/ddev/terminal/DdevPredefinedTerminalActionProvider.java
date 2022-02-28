package de.php_perfect.intellij.ddev.terminal;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.project.Project;
import de.php_perfect.intellij.ddev.actions.DdevPredefinedTerminalAction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.plugins.terminal.ui.OpenPredefinedTerminalActionProvider;

import java.util.ArrayList;
import java.util.List;

public class DdevPredefinedTerminalActionProvider implements OpenPredefinedTerminalActionProvider {
    @NotNull
    @Override
    public List<AnAction> listOpenPredefinedTerminalActions(@NotNull Project project) {
        List<AnAction> actions = new ArrayList<>();
        actions.add(new DdevPredefinedTerminalAction());

        return actions;
    }
}
