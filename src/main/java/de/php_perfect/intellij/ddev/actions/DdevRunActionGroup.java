package de.php_perfect.intellij.ddev.actions;

import com.intellij.openapi.actionSystem.ActionGroup;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;

public class DdevRunActionGroup extends ActionGroup {
    @Override
    public AnAction @NotNull [] getChildren(AnActionEvent e) {
        return new AnAction[]{
                new DdevStartAction(),
                new DdevStopAction(),
                new DdevRestartAction(),
                new DdevPoweroffAction(),
                new DdevDeleteAction(),
                new DdevShareAction(),
                new DdevConfigAction(),
        };
    }
}
