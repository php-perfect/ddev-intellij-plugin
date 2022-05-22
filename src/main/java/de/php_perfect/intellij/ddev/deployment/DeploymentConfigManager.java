package de.php_perfect.intellij.ddev.deployment;

import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

public interface DeploymentConfigManager {
    void configure(@NotNull DeploymentConfig deploymentConfig);

    static DeploymentConfigManager getInstance(@NotNull Project project) {
        return project.getService(DeploymentConfigManager.class);
    }
}
