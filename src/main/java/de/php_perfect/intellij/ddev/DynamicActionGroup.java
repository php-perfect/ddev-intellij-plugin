package de.php_perfect.intellij.ddev;

import com.intellij.openapi.actionSystem.ActionGroup;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import de.php_perfect.intellij.ddev.actions.PopupDialogAction;
import org.jetbrains.annotations.NotNull;

public class DynamicActionGroup extends ActionGroup {
    @Override
    public AnAction @NotNull [] getChildren(AnActionEvent e) {
        return new AnAction[] {
                new PopupDialogAction("Up"),
                new PopupDialogAction("Reload"),
                new PopupDialogAction("Provision"),
                new PopupDialogAction("Suspend"),
                new PopupDialogAction("Resume"),
                new PopupDialogAction("Halt"),
                new PopupDialogAction("Destroy"),
                new PopupDialogAction("Share"),
                new PopupDialogAction("Init in Project Root")
        };
    }
}
