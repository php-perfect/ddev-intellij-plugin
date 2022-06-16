package de.php_perfect.intellij.ddev.php;

import com.intellij.openapi.project.Project;
import com.jetbrains.php.config.PhpLanguageLevel;
import de.php_perfect.intellij.ddev.DdevConfigArgumentProvider;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public final class PhpVersionArgumentProvider implements DdevConfigArgumentProvider {
    @Override
    public @NotNull List<@NotNull String> getAdditionalArguments(@NotNull Project project) {
        return List.of("--php-version", PhpLanguageLevel.current(project).getPresentableName());
    }
}
