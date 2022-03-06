package de.php_perfect.intellij.ddev.actions;

import com.intellij.ide.BrowserUtil;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.NlsActions;
import de.php_perfect.intellij.ddev.cmd.Description;
import de.php_perfect.intellij.ddev.state.DdevStateManager;
import de.php_perfect.intellij.ddev.state.State;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.net.URL;

public final class OpenServiceAction extends DdevAwareAction {
    private final @NotNull URL url;

    public OpenServiceAction(@NotNull URL url, @NotNull @NlsActions.ActionText String text, @Nullable @NlsActions.ActionDescription String description, @Nullable Icon icon) {
        super(text, description, icon);
        this.url = url;
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        BrowserUtil.browse(this.url);
    }

    @Override
    protected boolean isActive(@NotNull Project project) {
        final State state = DdevStateManager.getInstance(project).getState();

        if (!state.isInstalled() || !state.isConfigured()) {
            return false;
        }

        Description description = state.getDescription();

        if (description == null) {
            return false;
        }

        return description.getStatus() == Description.Status.RUNNING;
    }
}
