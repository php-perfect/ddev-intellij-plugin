package de.php_perfect.intellij.ddev.php;

import com.intellij.openapi.project.Project;
import com.jetbrains.php.config.interpreters.PhpInterpreter;
import org.jetbrains.annotations.NotNull;

public interface DdevPhpInterpreterManager {
    void updateDdevPhpInterpreter(@NotNull PhpInterpreter phpInterpreter);

    static DdevPhpInterpreterManager getInstance(@NotNull Project project) {
        return project.getService(DdevPhpInterpreterManager.class);
    }
}
