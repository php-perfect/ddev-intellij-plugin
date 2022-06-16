package de.php_perfect.intellij.ddev;

import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface DdevConfigArgumentProvider {
    @NotNull List<@NotNull String> getAdditionalArguments(@NotNull Project project);
}
