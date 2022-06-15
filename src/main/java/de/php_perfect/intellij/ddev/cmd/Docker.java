package de.php_perfect.intellij.ddev.cmd;

import com.intellij.openapi.application.ApplicationManager;

public interface Docker {
    boolean isRunning(String workDirectory);

    static Docker getInstance() {
        return ApplicationManager.getApplication().getService(Docker.class);
    }
}
