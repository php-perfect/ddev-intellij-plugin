package de.php_perfect.intellij.ddev.node;

import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

public interface NodeInterpreterProvider {
    void configureNodeInterpreter(@NotNull NodeInterpreterConfig nodeInterpreterConfig);

    static NodeInterpreterProvider getInstance(@NotNull Project project) {
        return project.getService(NodeInterpreterProvider.class);
    }
}
