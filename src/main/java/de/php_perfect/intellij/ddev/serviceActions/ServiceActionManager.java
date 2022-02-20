package de.php_perfect.intellij.ddev.serviceActions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.project.Project;
import de.php_perfect.intellij.ddev.state.State;
import org.jetbrains.annotations.NotNull;

public interface ServiceActionManager {

    AnAction @NotNull [] getServiceActions();

    void updateActionsByState(@NotNull State state);

    static ServiceActionManager getInstance(@NotNull Project project) {
        return project.getService(ServiceActionManager.class);
    }
}
