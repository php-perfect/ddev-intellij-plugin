package de.php_perfect.intellij.ddev;

import com.intellij.openapi.actionSystem.ActionGroup;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;

public class DynamicToolMenuEntry extends ActionGroup {
    @Override
    public AnAction @NotNull [] getChildren(AnActionEvent e) {
        return new AnAction[] {
                new DdevButtonAction("Start", "start"),
                new DdevButtonAction("Stop", "stop"),
                new DdevButtonAction("Restart", "restart"),
                new DdevButtonAction("Poweroff",  "poweroff"),
                new DdevButtonAction("Delete",  "delete"),
                new DdevButtonAction("Share",  "share"),
                new DdevButtonAction("Init in Project Root", "config")
        };
    }
}
