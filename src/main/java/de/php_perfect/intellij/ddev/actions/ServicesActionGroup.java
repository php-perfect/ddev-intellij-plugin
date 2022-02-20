package de.php_perfect.intellij.ddev.actions;

import com.intellij.openapi.actionSystem.ActionGroup;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAware;
import de.php_perfect.intellij.ddev.serviceActions.ServiceActionManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ServicesActionGroup extends ActionGroup implements DumbAware {
    @Override
    public AnAction @NotNull [] getChildren(@Nullable AnActionEvent e) {

        if (e == null || e.getProject() == null) {
            return new AnAction[0];
        }

        return ServiceActionManager.getInstance(e.getProject()).getServiceActions();
    }
}
