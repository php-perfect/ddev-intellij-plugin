package de.php_perfect.intellij.ddev.cmd;

import com.intellij.openapi.application.ApplicationManager;
import org.jetbrains.annotations.NotNull;

public interface Docker {
    boolean isRunning(String workDirectory);

    @NotNull String getContext(String workDirectory);

    static Docker getInstance() {
        return ApplicationManager.getApplication().getService(Docker.class);
    }
}
