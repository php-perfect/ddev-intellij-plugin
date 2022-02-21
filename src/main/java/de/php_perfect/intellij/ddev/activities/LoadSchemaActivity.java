package de.php_perfect.intellij.ddev.activities;

import com.intellij.openapi.project.Project;
import de.php_perfect.intellij.ddev.DdevAwareActivity;
import de.php_perfect.intellij.ddev.config.SchemaProvider;
import de.php_perfect.intellij.ddev.state.DdevStateManager;
import de.php_perfect.intellij.ddev.state.State;
import org.jetbrains.annotations.NotNull;

public final class LoadSchemaActivity implements DdevAwareActivity {
    @Override
    public void runActivity(@NotNull Project project) {
        State state = DdevStateManager.getInstance(project).getState();
        if (state.getVersions() != null && state.getVersions().getDdevVersion() != null) {
            SchemaProvider.getInstance().loadSchema(state.getVersions().getDdevVersion());
        }
    }
}
