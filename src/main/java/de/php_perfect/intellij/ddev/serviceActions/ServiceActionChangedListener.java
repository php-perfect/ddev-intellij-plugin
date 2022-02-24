package de.php_perfect.intellij.ddev.serviceActions;

import com.intellij.openapi.project.Project;
import de.php_perfect.intellij.ddev.DdevStateChangedListener;
import de.php_perfect.intellij.ddev.state.State;
import org.jetbrains.annotations.NotNull;

public class ServiceActionChangedListener implements DdevStateChangedListener {
    private final @NotNull Project project;

    public ServiceActionChangedListener(@NotNull Project project) {
        this.project = project;
    }

    @Override
    public void onDdevChanged(State state) {
        ServiceActionManager.getInstance(this.project).updateActionsByState(state);
    }
}
