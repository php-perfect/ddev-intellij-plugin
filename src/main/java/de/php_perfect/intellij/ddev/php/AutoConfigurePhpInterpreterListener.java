package de.php_perfect.intellij.ddev.php;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.jetbrains.php.config.interpreters.PhpInterpreter;
import de.php_perfect.intellij.ddev.DescriptionChangedListener;
import de.php_perfect.intellij.ddev.cmd.Description;
import de.php_perfect.intellij.ddev.settings.DdevSettingsState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

// @todo: Reduce Logic in Listener
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

        if (!DdevSettingsState.getInstance(this.project).autoConfigurePhpInterpreter) {
            return;
        }

        VirtualFile composeFile = DdevComposeFileLoader.getInstance(this.project).load();

        if (composeFile == null || !composeFile.exists()) {
            return;
        }

        PhpInterpreter phpInterpreter = PhpInterpreterProvider.getInstance(this.project).buildDdevPhpInterpreter(description, composeFile.getPath());

        if (phpInterpreter == null) {
            return;
        }

        DdevPhpInterpreterManager.getInstance(this.project).updateDdevPhpInterpreter(phpInterpreter);
    }
}
