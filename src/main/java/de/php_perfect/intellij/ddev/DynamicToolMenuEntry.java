package de.php_perfect.intellij.ddev;

import com.intellij.openapi.actionSystem.ActionGroup;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;

public class DynamicToolMenuEntry extends ActionGroup {
    @Override
    public AnAction @NotNull [] getChildren(AnActionEvent e) {
        return new AnAction[] {
                new DdevButtonAction("Up", "start"),
                new DdevButtonAction("Reload"),
                new DdevButtonAction("Provision"),
                new DdevButtonAction("Suspend"),
                new DdevButtonAction("Resume"),
                new DdevButtonAction("Halt"),
                new DdevButtonAction("Destroy"),
                new DdevButtonAction("Share"),
                new DdevButtonAction("Init in Project Root")
        };
    }
}
