package de.php_perfect.intellij.ddev.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAware;
import de.php_perfect.intellij.ddev.version.VersionChecker;
import org.jetbrains.annotations.NotNull;

public final class CheckVersionAction extends AnAction implements DumbAware {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        if (e.getProject() == null) {
            return;
        }

        VersionChecker.getInstance(e.getProject()).checkDdevVersion(true);
    }
}
