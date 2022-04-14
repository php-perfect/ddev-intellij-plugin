package de.php_perfect.intellij.ddev.php;

import com.intellij.openapi.project.Project;
import com.jetbrains.php.config.PhpLanguageLevel;
import de.php_perfect.intellij.ddev.PhpVersionProvider;
import org.jetbrains.annotations.NotNull;

public final class LanguageLevelPhpVersionProvider implements PhpVersionProvider {
    @Override
    public String getVersion(@NotNull Project project) {
        return PhpLanguageLevel.current(project).getPresentableName();
    }
}
