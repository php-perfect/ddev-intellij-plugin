package de.php_perfect.intellij.ddev.php;

import com.intellij.openapi.project.Project;
import com.jetbrains.php.config.interpreters.PhpInterpreter;
import de.php_perfect.intellij.ddev.cmd.Description;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface PhpInterpreterProvider {
    @Nullable PhpInterpreter buildDdevPhpInterpreter(@NotNull Description description, @NotNull String composeFilePath);

    static PhpInterpreterProvider getInstance(@NotNull Project project) {
        return project.getService(PhpInterpreterProvider.class);
    }
}
