package de.php_perfect.intellij.ddev.php;

import com.intellij.openapi.project.Project;
import de.php_perfect.intellij.ddev.cmd.Description;
import org.jetbrains.annotations.NotNull;

public interface ConfigurationProvider {
    void configure(@NotNull Description description);

    static ConfigurationProvider getInstance(@NotNull Project project) {
        return project.getService(ConfigurationProvider.class);
    }
}
