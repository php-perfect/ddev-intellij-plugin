package de.php_perfect.intellij.ddev;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.util.NlsActions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.logging.Logger;

public class DdevButtonAction extends AnAction {
    private final String ddevAction;

    public DdevButtonAction(@NotNull @NlsActions.ActionText String text, @Nullable Icon icon, @NotNull String ddevAction) {
        super(text, null, icon);

        this.ddevAction = ddevAction;
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        if (e.getProject() == null) {
            Logger.getGlobal().warning("No active project found");
            return;
        }

        DdevExecutor.getInstance(e.getProject()).runDdev(e.getPresentation().getText(), ddevAction);
    }
}
