package de.php_perfect.intellij.ddev.php;

import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

public interface PhpInterpreterProvider {
    void registerInterpreter(@NotNull DdevInterpreterConfig interpreterConfig);

    static PhpInterpreterProvider getInstance(@NotNull Project project) {
        return project.getService(PhpInterpreterProvider.class);
    }
}
