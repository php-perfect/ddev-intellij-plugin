package de.php_perfect.intellij.ddev.activities;

import com.intellij.openapi.project.Project;
import de.php_perfect.intellij.ddev.DdevAwareActivity;
import de.php_perfect.intellij.ddev.database.DataSourceGenerator;
import de.php_perfect.intellij.ddev.state.DdevStateManager;
import de.php_perfect.intellij.ddev.state.State;
import org.jetbrains.annotations.NotNull;

public class RegisterDataSourceActivity implements DdevAwareActivity {
    @Override
    public void runActivity(@NotNull Project project) {
        State ddevState = DdevStateManager.getInstance(project).getState();

        DataSourceGenerator.getInstance(project).generateDataSource(ddevState);
    }
}
