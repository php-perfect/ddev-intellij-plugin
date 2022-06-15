package de.php_perfect.intellij.ddev.state;

import com.intellij.openapi.project.Project;
import de.php_perfect.intellij.ddev.StateChangedListener;
import de.php_perfect.intellij.ddev.cmd.Description;
import de.php_perfect.intellij.ddev.notification.DdevNotifier;
import org.jetbrains.annotations.NotNull;

public final class UnknownStateListener implements StateChangedListener {
    private final Project project;

    public UnknownStateListener(Project project) {
        this.project = project;
    }

    @Override
    public void onDdevChanged(@NotNull State state) {
        if (!state.isAvailable() || !state.isConfigured()) {
            return;
        }

        Description description = state.getDescription();

        if (description == null || description.getStatus() == null) {
            DdevNotifier.getInstance(this.project).notifyUnknownStateEntered();
        }
    }
}
