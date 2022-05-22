package de.php_perfect.intellij.ddev.deployment;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.io.FileUtil;
import de.php_perfect.intellij.ddev.DescriptionChangedListener;
import de.php_perfect.intellij.ddev.cmd.Description;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public final class ConfigureDeploymentListener implements DescriptionChangedListener {
    private final @NotNull Project project;

    public ConfigureDeploymentListener(@NotNull Project project) {
        this.project = project;
    }

    @Override
    public void onDescriptionChanged(@Nullable Description description) {
        if (description == null) {
            return;
        }

        DeploymentConfig deploymentConfig = new DeploymentConfig(
                FileUtil.toSystemDependentName(Objects.requireNonNull(this.project.getBasePath())),
                description.getPrimaryUrl() != null ? description.getPrimaryUrl() : ""
        );

        DeploymentConfigManager.getInstance(this.project).configure(deploymentConfig);
    }
}
