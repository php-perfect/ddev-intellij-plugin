package de.php_perfect.intellij.ddev;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.ActionGroup;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;

public class DynamicToolMenuEntry extends ActionGroup {
    @Override
    public AnAction @NotNull [] getChildren(AnActionEvent e) {
        return new AnAction[] {
                new DdevButtonAction("Start", AllIcons.Actions.Execute, "start"),
                new DdevButtonAction("Stop", AllIcons.Actions.Pause, "stop"),
                new DdevButtonAction("Restart", AllIcons.Actions.Refresh, "restart"),
                new DdevButtonAction("Poweroff", AllIcons.Actions.Suspend, "poweroff"),
                new DdevButtonAction("Delete", AllIcons.Actions.Cancel, "delete"),
                new DdevButtonAction("Share", AllIcons.Actions.Share, "share"),
                new DdevButtonAction("Init in Project Root", null, "config")
        };
    }
}
