package de.php_perfect.intellij.ddev;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.util.NlsActions;
import de.php_perfect.intellij.ddev.DdevExecutor;
import icons.DdevIcons;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Logger;

public class DdevButtonAction extends AnAction {
    private final String[] cmdArgs;

    public DdevButtonAction(@NotNull @NlsActions.ActionText String text, String... cmdArgs) {
        super(text, null, DdevIcons.Sdk_default_icon);

        this.cmdArgs = cmdArgs;
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        if (e.getProject() == null) {
            Logger.getGlobal().warning("No active project found");
            return;
        }

        DdevExecutor.getInstance(e.getProject()).runDdev(cmdArgs);
    }
}
