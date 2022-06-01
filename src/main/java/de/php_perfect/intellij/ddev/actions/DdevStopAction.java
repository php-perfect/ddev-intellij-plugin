package de.php_perfect.intellij.ddev.actions;

import com.intellij.openapi.project.Project;
import de.php_perfect.intellij.ddev.cmd.DdevRunner;
import de.php_perfect.intellij.ddev.cmd.Description;
import de.php_perfect.intellij.ddev.state.DdevStateManager;
import de.php_perfect.intellij.ddev.state.State;
import org.jetbrains.annotations.NotNull;

public final class DdevStopAction extends DdevRunAction {
    @Override
    protected void run(@NotNull Project project) {
        DdevRunner.getInstance().stop(project);
    }

    @Override
    protected boolean isActive(@NotNull Project project) {
        final State state = DdevStateManager.getInstance(project).getState();

        if (!state.isAvailable()) {
            return false;
        }

        Description description = state.getDescription();

        if (description == null) {
            return true;
        }

        return description.getStatus() == Description.Status.RUNNING || description.getStatus() == null;
    }
}
