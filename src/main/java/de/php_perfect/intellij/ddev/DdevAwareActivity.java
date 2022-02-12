package de.php_perfect.intellij.ddev;

import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

public interface DdevAwareActivity {
    void runActivity(@NotNull Project project);
}
