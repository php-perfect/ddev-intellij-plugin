package de.php_perfect.intellij.ddev.php;

import com.intellij.openapi.project.Project;
import de.php_perfect.intellij.ddev.DescriptionChangedListener;
import de.php_perfect.intellij.ddev.cmd.Description;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AutoConfigurePhpInterpreterListener implements DescriptionChangedListener {
    private final @NotNull Project project;

    public AutoConfigurePhpInterpreterListener(@NotNull Project project) {
        this.project = project;
    }

    @Override
    public void onDescriptionChanged(@Nullable Description description) {
        if (description == null) {
            return;
        }

        ConfigurationProvider.getInstance(this.project).configure(description);
    }
}
