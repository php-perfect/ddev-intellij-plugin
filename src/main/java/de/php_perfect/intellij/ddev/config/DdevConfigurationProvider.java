package de.php_perfect.intellij.ddev.config;

import com.intellij.openapi.project.Project;
import de.php_perfect.intellij.ddev.cmd.Description;
import org.jetbrains.annotations.NotNull;

public interface DdevConfigurationProvider {

    Description getDdevConfig() throws DdevConfigurationException;

    void update() throws DdevConfigurationException;

    static DdevConfigurationProvider getInstance(@NotNull Project project) {
        return project.getService(DdevConfigurationProvider.class);
    }
}
