package de.php_perfect.intellij.ddev.php.server;

import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

public interface ServerConfigManager {
    void configure(@NotNull ServerConfig serverConfig);

    static ServerConfigManager getInstance(@NotNull Project project) {
        return project.getService(ServerConfigManager.class);
    }
}
